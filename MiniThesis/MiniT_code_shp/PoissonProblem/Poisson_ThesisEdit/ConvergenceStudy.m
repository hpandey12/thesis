for gausspts = 6:3:12 %4, 8, 12
    for approximation = 1:5
        if approximation > gausspts
            continue;
        end
        for elements = 1:25 %upto 15x15
            Ar = Poisson([elements elements], approximation, gausspts);
            filename = sprintf('Convergence%d.txt', gausspts);
            if isfile(filename)
                writematrix(Ar, filename, 'WriteMode', 'append');
            else
               writematrix(Ar, filename);
            end
        end
   end
end