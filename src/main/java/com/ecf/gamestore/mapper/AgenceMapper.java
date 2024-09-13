package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.AgenceDTO;
import com.ecf.gamestore.models.Agence;
import com.ecf.gamestore.utils.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AgenceMapper {

    public static AgenceDTO toDTO(Agence agence) {
        if(Objects.isNull(agence)) return null;
        AgenceDTO dto = new AgenceDTO();
        dto.setUuid(agence.getUuid());
        dto.setName(agence.getName());
        dto.setAddress(agence.getAddress());
        dto.setCodePostal(agence.getCodePostal());
        dto.setCity(agence.getCity());
        dto.setCountry(agence.getCountry());
        return dto;
    }

    public static List<AgenceDTO> toDTOs(List<Agence> agences) {
        if(CollectionUtils.isNullOrEmpty(agences)) return List.of();
        return agences.stream()
                .map(AgenceMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
