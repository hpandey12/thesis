# solver.py
from __future__ import annotations  # For forward references in type hints
from abc import ABC, abstractmethod


class Solver(ABC):
    """
    Abstract base class defining a solver interface.
    Concrete solver classes (e.g., StefanSolver) should extend this class
    and implement its methods using the data and parameters in a DynamicModel.
    """

    def __init__(self, model: 'DynamicModel') -> None:
        """
        Initialize the solver with a reference to a model.
        The model should expose .params (dict) containing necessary parameters.
        """
        self.model = model

