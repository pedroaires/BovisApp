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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
    void deveCadastrarBoi() {
        BoiRequestDTO boiRequestDTO = new BoiRequestDTO();
        boiRequestDTO.setNumero(123);
        boiRequestDTO.setRaca("Angus");
        boiRequestDTO.setLoteId(null);

        String estadoBoiStr = "na_fazenda";
        boiRequestDTO.setEstadoBoi(estadoBoiStr);

        Raca raca = new Raca();
        when(racaService.getRacaByNome("Angus")).thenReturn(raca);

        Lote lote = new Lote();
        when(loteService.getLoteById(null)).thenReturn(lote);

        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);
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
    @Disabled
    void deveCadastrarBoiLoteNaoNulo() {
        BoiRequestDTO boiRequestDTO = new BoiRequestDTO();
        boiRequestDTO.setNumero(123);
        boiRequestDTO.setRaca("Angus");
        boiRequestDTO.setLoteId(1L);
        boiRequestDTO.setEstadoBoi("na_fazenda");

        Raca raca = new Raca();
        when(racaService.getRacaByNome("Angus")).thenReturn(raca);

        Lote lote = new Lote();
        when(loteService.getLoteById(1L)).thenReturn(lote);

        when(EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi())).thenReturn(EstadoBoi.NA_FAZENDA);

        BoiResponseDTO response = boiService_underTest.cadastraBoi(boiRequestDTO);

        Boi boi = new Boi(
                boiRequestDTO.getNumero(),
                raca,
                lote,
                EstadoBoi.NA_FAZENDA
        );

        verify(loteService).getLoteById(null);
        verify(racaService).getRacaByNome("Angus");

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boi);
    }
    @Test
    @Disabled
    void naoDeveCadastrarBoiEstadoNulo() {
        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                123,
                "nelore",
                null,
                null

        );


        Raca raca = new Raca();
        when(racaService.getRacaByNome("nelore")).thenReturn(raca);

        Lote lote = new Lote();
        when(loteService.getLoteById(null)).thenReturn(lote);

        when(EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi())).thenReturn(EstadoBoi.NA_FAZENDA);
        BoiResponseDTO response = boiService_underTest.cadastraBoi(boiRequestDTO);

        Boi boi = new Boi(
                boiRequestDTO.getNumero(),
                raca,
                lote,
                EstadoBoi.NA_FAZENDA
        );

        verify(loteService).getLoteById(null);
        verify(racaService).getRacaByNome("Angus");

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boi);
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