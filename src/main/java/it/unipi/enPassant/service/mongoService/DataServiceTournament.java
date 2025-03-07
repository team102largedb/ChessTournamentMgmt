package it.unipi.enPassant.service.mongoService;

import it.unipi.enPassant.model.requests.mongoModel.tournament.DocumentTournament;
import it.unipi.enPassant.model.requests.mongoModel.tournament.*;
import it.unipi.enPassant.model.requests.mongoModel.user.DocumentUser;
import it.unipi.enPassant.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataServiceTournament {
    @Autowired
    private TournamentRepository tournamentRepository;

    public List<TournamentModel> tournamentGetList(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<DocumentTournament> page = tournamentRepository.findAll(pageable);
        List<DocumentTournament> list = page.getContent();

        return list.stream()
                .map(doc -> new TournamentModel( doc.getEdition(), doc.getCategory(), doc.getLocation()))
                .collect(Collectors.toList());
    }

    public List<MatchListModel> tournamentMatchGetList(String category,int edition,String location) {
        return tournamentRepository.findTournamentMatches(edition, category, location);
    }

    public DataTournamentMatchModel tournamentMatchGet(String category, int edition, String location, String White, String Black) {
        DataTournamentMatchModel tournament = tournamentRepository.findMatchofTournament(edition, category, location, White, Black);
        return tournament;
    }

    public Boolean updatewinner(int edition, String category, String location) {
        System.out.println(edition);
        System.out.println(category);
        System.out.println(location);
        String winner = tournamentRepository.findWinnerByEditionCategoryLocation(edition, category, location);
        System.out.println(winner);
        if (winner != null && !winner.isEmpty()) {
            Optional<DocumentTournament> tournamentOpt = tournamentRepository.findByEditionAndCategoryAndLocation(edition, category, location);

            if (tournamentOpt.isPresent()) {
                DocumentTournament tournament = tournamentOpt.get();
                tournament.setWinner(winner);
                tournamentRepository.save(tournament);
                return true;
            }
        }
        return false;
    }
}
