# rt_solver.py
from __future__ import annotations
import logging
import numpy as np
from typing import List, Tuple

from mod_solver import Solver               # dein ABC

class RayleighTaylorSolver(Solver):
    """
    Post-Processing-Modul für eine Rayleigh-Taylor-Simulation.
    Erwartet, dass model.solution_data eine Liste aus Dictionaries enthält,
    in denen u. a. folgende Keys vorkommen:
        'y_dat'   : Höhenwerte (1-D oder abgeflacht)
        'v_y'     : Vertikale Geschwindigkeiten an denselben Punkten
    """

    # ------------------------------------------------------------------
    #  Konstruktor
    # ------------------------------------------------------------------
    def __init__(self, model: 'DynamicModel') -> None:
        super().__init__(model)

    # ------------------------------------------------------------------
    #  Hauptauswertung
    # ------------------------------------------------------------------
    def calculate_stuff(self) -> None:
        """
        Ermittelt Blasen-/Spitzen­position & ‑geschwindigkeit pro Zeitschritt
        und legt die Ergebnisse in model.analysis_data ab.
        """
        if not self.model.solution_data:
            logging.warning("RayleighTaylorSolver: model.solution_data ist leer.")
            return

        y_bub:   List[float] = []
        y_spike: List[float] = []
        v_bub_file:   List[float] = []
        v_spike_file: List[float] = []

        for idx, timestep_data in enumerate(self.model.solution_data):
            try:
                y_vals = timestep_data["y_dat"]
                v_y    = timestep_data["v_y"]
            except KeyError as e:
                logging.error(f"RayleighTaylorSolver: Key {e} in solution_data[{idx}] fehlt.")
                continue

            # Positiv nach oben: Blase = höchster Punkt, Spike = niedrigster Punkt
            y_bub_inst   = float(np.max(y_vals) - 2.0)
            y_spike_inst = float(np.min(y_vals) - 2.0)

            # An den jeweiligen y-Extrema die zugehörige v_y ermitteln
            v_bub_file.append(float(v_y[np.argmax(y_vals)]))
            v_spike_file.append(float(v_y[np.argmin(y_vals)]))

            # Ablage im Roh-Dict (optional, aber praktisch)
            timestep_data["y_bub"]   = y_bub_inst
            timestep_data["y_spike"] = y_spike_inst

            y_bub.append(y_bub_inst)
            y_spike.append(y_spike_inst)

        # Geschwindigkeiten aus aufeinanderfolgenden Positionen (|Δy|/Δt)
        # Achtung: Länge ist N-1!
        try:
            dt = np.diff(self.model.params["timesteps"])
        except KeyError:
            logging.warning("RayleighTaylorSolver: 'timesteps' nicht in model.params – Differenzen werden als 1 angenommen.")
            dt = np.ones(len(y_bub) - 1)

        v_bub   = np.abs(np.diff(y_bub)   / dt)
        v_spike = np.abs(np.diff(y_spike) / dt)

        # -----------------------------
        #  Ablage in analysis_data
        # -----------------------------
        adict = self.model.analysis_data          # Alias
        adict["y_bub"]        = np.asarray(y_bub)
        adict["y_spike"]      = np.asarray(y_spike)
        adict["v_bub"]        = np.asarray(v_bub)
        adict["v_spike"]      = np.asarray(v_spike)
        adict["v_bub_file"]   = np.asarray(v_bub_file)
        adict["v_spike_file"] = np.asarray(v_spike_file)
        adict["nondim_time"]  = np.asarray(self.model.params.get("nondim_time_time", []))

        logging.info("RayleighTaylorSolver: Analyse abgeschlossen.")

    # ------------------------------------------------------------------
    #  Hilfsfunktionen für Fehler/Interpolation
    # ------------------------------------------------------------------
    @staticmethod
    def find_nearest(array: np.ndarray, value: float) -> Tuple[int, float]:
        idx = (np.abs(array - value)).argmin()
        return idx, array[idx]

    @staticmethod
    def interpolate_central_difference(
        y_val: np.ndarray,
        timesteps: np.ndarray,
        time: List[float]
    ) -> np.ndarray:
        """
        Interpoliert die Größe y_val auf beliebige Zeitpunkte 'time'
        mittels Zentral-, Vorwärts- oder Rückwärtsdifferenz.
        """
        interpolated_values = []

        for t in time:
            idx_nearest, t_nearest = RayleighTaylorSolver.find_nearest(timesteps, t)

            if 0 < idx_nearest < len(timesteps) - 1:          # zentral
                delta_t = timesteps[idx_nearest + 1] - timesteps[idx_nearest - 1]
                f_plus  = y_val[idx_nearest + 1]
                f_minus = y_val[idx_nearest - 1]
                interp_value = f_minus + ((f_plus - f_minus) / delta_t) * (t - timesteps[idx_nearest - 1])

            elif idx_nearest == 0:                             # vorwärts
                delta_t = timesteps[1] - timesteps[0]
                interp_value = y_val[0] + ((y_val[1] - y_val[0]) / delta_t) * (t - timesteps[0])

            else:                                              # rückwärts
                delta_t = timesteps[-1] - timesteps[-2]
                interp_value = y_val[-2] + ((y_val[-1] - y_val[-2]) / delta_t) * (t - timesteps[-2])

            interpolated_values.append(interp_value)

        return np.asarray(interpolated_values)

    @staticmethod
    def calc_errors(dns_ar: np.ndarray, model_ar: np.ndarray) -> Tuple[float, List[float]]:
        """
        Gibt L2-Fehler (global) und L_inf pro Punkt zurück.
        """
        if len(dns_ar) != len(model_ar):
            raise ValueError("calc_errors: Arrays müssen gleiche Länge haben.")

        err = np.abs(dns_ar - model_ar)
        L2_err   = np.sqrt(np.sum(err ** 2))
        Linf_err = err.tolist()
        return L2_err, Linf_err