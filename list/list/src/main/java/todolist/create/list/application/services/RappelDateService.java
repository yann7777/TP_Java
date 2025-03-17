package todolist.create.list.application.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import todolist.create.list.domain.model.Tache;
import todolist.create.list.application.ports.input.TacheUseCase;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RappelDateService {
    
    private final TacheUseCase tacheUseCase;

    public RappelDateService(TacheUseCase tacheUseCase){
        this.tacheUseCase = tacheUseCase;
    }

    @Scheduled(fixedRate = 60000)
    public void verifiedRappels(){
        LocalDateTime maintenant = LocalDateTime.now();
        List<Tache> taches = tacheUseCase.getAllTaches();

        for(Tache tache : taches){
            if(tache.getDateRappel() != null && tache.getDateRappel().isBefore(maintenant)){
                System.out.println("Rappel pour les tâches : " + tache.getTitre());
            }
        }
    }
}
