package it.powercolle.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link it.powercolle.entity.Listino} entity
 */
@Data
public class ListinoPdfDto implements Serializable {
    private String data;
    private List<TipoProdottoPdfDto> categoriaList;
}