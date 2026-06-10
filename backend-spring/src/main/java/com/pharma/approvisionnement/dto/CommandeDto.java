package com.pharma.approvisionnement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pharma.approvisionnement.entity.Commande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandeDto {
    private Long id;
    private String nomMed;
    private String grossiste;
    private Integer quantite;
    private Double prixTotal;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCmd;
    
    private String statut;

    public static CommandeDto fromEntity(Commande c) {
        if (c == null) return null;
        return CommandeDto.builder()
                .id(c.getId())
                .nomMed(c.getNomMed())
                .grossiste(c.getGrossiste())
                .quantite(c.getQuantite())
                .prixTotal(c.getPrixTotal())
                .dateCmd(c.getDateCmd())
                .statut(c.getStatut() != null ? c.getStatut().getLabel() : null)
                .build();
    }
}
