package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.Raca;
import bovisApp.firstApp.model.enumeration.EstadoBoi;
import bovisApp.firstApp.repository.BoiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BoiServiceImplTest {

    private BoiService boiService_underTest;

    @Mock
    private BoiRepository boiRepository;

    @Mock
    private LoteService loteService;

    @Mock
    private RacaService racaService;

    @BeforeEach
    void setUp() {
        boiService_underTest = new BoiServiceImpl(boiRepository, loteService, racaService);
    }

    @Test
    void testGetBois() {
        boiService_underTest.getBois();

        verify(boiRepository).findAll();
    }

    @Test
    void deveCadastrarBoiLoteNulo() {
        BoiRequestDTO boiRequestDTO = new BoiRequestDTO();
        boiRequestDTO.setNumero(123);
        boiRequestDTO.setRaca("Angus");
        boiRequestDTO.setLoteId(null);

        String estadoBoiStr = "na_fazenda";
        boiRequestDTO.setEstadoBoi(estadoBoiStr);
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca("Angus");
        when(racaService.getRacaByNome("Angus")).thenReturn(raca);

        Lote lote = new Lote();
        when(loteService.getLoteById(null)).thenReturn(lote);

        BoiResponseDTO response = boiService_underTest.cadastraBoi(boiRequestDTO);

        Boi boi = new Boi(
                boiRequestDTO.getNumero(),
                raca,
                lote,
                estadoBoi
        );

        verify(loteService).getLoteById(null);
        verify(racaService).getRacaByNome("Angus");

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boi);
        assertThat(response).isEqualTo(new BoiResponseDTO(boi));
    }
    @Test
    void deveCadastrarBoiLoteNaoNulo() {
        BoiRequestDTO boiRequestDTO = new BoiRequestDTO();
        boiRequestDTO.setNumero(123);
        boiRequestDTO.setRaca("Angus");
        boiRequestDTO.setLoteId(1L);

        String estadoBoiStr = "na_fazenda";
        boiRequestDTO.setEstadoBoi(estadoBoiStr);
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca("Angus");
        when(racaService.getRacaByNome("Angus")).thenReturn(raca);

        Lote lote = new Lote();
        lote.setId(1L);
        when(loteService.getLoteById(1L)).thenReturn(lote);

        BoiResponseDTO response = boiService_underTest.cadastraBoi(boiRequestDTO);

        Boi boi = new Boi(
                boiRequestDTO.getNumero(),
                raca,
                lote,
                estadoBoi
        );

        verify(loteService).getLoteById(1L);
        verify(racaService).getRacaByNome("Angus");

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boi);
        assertThat(response).isEqualTo(new BoiResponseDTO(boi));
    }

    @Test
    @Disabled
    void editaBoi() {
    }

    @Test
    @Disabled
    void deleteBoi() {
    }
}