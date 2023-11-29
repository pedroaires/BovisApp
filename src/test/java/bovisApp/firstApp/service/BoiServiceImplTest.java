package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.exception.BoiExistsException;
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
    void deveCadastrarBoi() {
        Long loteId = 1L;
        String racaStr = "Angus";
        String estadoBoiStr = "na_fazenda";
        Integer numeroBoi = 123;

        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                numeroBoi,
                racaStr,
                loteId,
                estadoBoiStr
        );

        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);

        Lote lote = new Lote();
        when(loteService.getLoteById(loteId)).thenReturn(lote);

        BoiResponseDTO response = boiService_underTest.cadastraBoi(boiRequestDTO);
        Boi expectedBoi = new Boi(
                boiRequestDTO.getNumero(),
                raca,
                lote,
                estadoBoi
        );

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        verify(loteService).getLoteById(loteId);
        verify(racaService).getRacaByNome(racaStr);

        assertThat(boiArgumentCaptor.getValue()).isEqualTo(expectedBoi);
        assertThat(response).isEqualTo(new BoiResponseDTO(expectedBoi));
    }
    @Test
    void deveCadastrarBoiLoteNulo() {
        Long loteId = null;
        String racaStr = "Angus";
        String estadoBoiStr = "na_fazenda";
        Integer numeroBoi = 123;

        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                numeroBoi,
                racaStr,
                loteId,
                estadoBoiStr
        );

        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);

        Lote lote = new Lote();
        when(loteService.getLoteById(loteId)).thenReturn(lote);

        BoiResponseDTO response = boiService_underTest.cadastraBoi(boiRequestDTO);
        Boi expectedBoi = new Boi(
                boiRequestDTO.getNumero(),
                raca,
                lote,
                estadoBoi
        );

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        verify(loteService).getLoteById(loteId);
        verify(racaService).getRacaByNome(racaStr);

        assertThat(boiArgumentCaptor.getValue()).isEqualTo(expectedBoi);
        assertThat(response).isEqualTo(new BoiResponseDTO(expectedBoi));
    }
    @Test
    void naoDeveCadastrarBoiNumeroNulo() {
        Long loteId = 1L;
        String racaStr = "Angus";
        String estadoBoiStr = "na_fazenda";
        Integer numeroBoi = null;

        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                numeroBoi,
                racaStr,
                loteId,
                estadoBoiStr
        );

        String expectedMsgException = "Numero do boi não pode ser vazio";
        assertThatThrownBy(() -> boiService_underTest.cadastraBoi(boiRequestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMsgException);
    }
    @Test
    void naoDeveCadastrarBoiEstadoNulo() {
        Long loteId = 1L;
        String racaStr = "Angus";
        String estadoBoiStr = null;
        Integer numeroBoi = 123;

        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                numeroBoi,
                racaStr,
                loteId,
                estadoBoiStr
        );

        String expectedMsgException = "Estado do boi não pode ser vazio";
        assertThatThrownBy(() -> boiService_underTest.cadastraBoi(boiRequestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMsgException);
    }

    @Test
    void naoDeveCadastrarBoiEstadoInvalido() {
        Long loteId = 1L;
        String racaStr = "Angus";
        String estadoBoiStr = "invalido";
        Integer numeroBoi = 123;

        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                numeroBoi,
                racaStr,
                loteId,
                estadoBoiStr
        );

        String expectedMsgException = "Estado do boi inválido: " + estadoBoiStr;
        assertThatThrownBy(() -> boiService_underTest.cadastraBoi(boiRequestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMsgException);
    }

    @Test
    void naoDeveCadastrarNumeroRepetidoMesmoLote() {
        Long loteId = 1L;
        String racaStr = "Angus";
        String estadoBoiStr = "na_fazenda";
        Integer numeroBoi = 123;

        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                numeroBoi,
                racaStr,
                loteId,
                estadoBoiStr
        );

        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);

        Lote lote = new Lote();
        when(loteService.getLoteById(loteId)).thenReturn(lote);

        BoiResponseDTO response = boiService_underTest.cadastraBoi(boiRequestDTO);

        when(boiRepository.existsByLoteAndNumero(lote, numeroBoi)).thenReturn(true);
        assertThatThrownBy(() -> boiService_underTest.cadastraBoi(boiRequestDTO))
                .isInstanceOf(BoiExistsException.class)
                .hasMessageContaining("Boi com número: " + numeroBoi + " já existe no Lote de id: " + loteId);

    }



    @Test
    void editaBoi() {
        Long boiId = 1L;
        Long loteId = 1L;
        String racaStr = "Angus";
        String estadoBoiStr = "na_fazenda";
        Integer numeroBoi = 123;

        Long novoLoteId = 2L;
        String novaRacaStr = "Nelore";
        String novoEstadoBoiStr = "morto";
        Integer novoNumeroBoi = 321;

        BoiRequestDTO boiRequestDTO = new BoiRequestDTO(
                novoNumeroBoi,
                novaRacaStr,
                novoLoteId,
                novoEstadoBoiStr
        );

        Raca raca = new Raca(racaStr);
        Raca novaRaca = new Raca(novaRacaStr);
        when(racaService.getRacaByNome(novaRacaStr)).thenReturn(novaRaca);

        Lote lote = new Lote();
        Lote novoLote = new Lote();
        when(loteService.getLoteById(novoLoteId)).thenReturn(novoLote);

        Boi boi = new Boi(
                numeroBoi,
                raca,
                lote,
                EstadoBoi.getEstadoBoi(estadoBoiStr)
        );
        Boi expectedBoi = new Boi(
                novoNumeroBoi,
                novaRaca,
                novoLote,
                EstadoBoi.getEstadoBoi(novoEstadoBoiStr)
        );

        when(boiRepository.findById(boiId)).thenReturn(java.util.Optional.of(boi));

        BoiResponseDTO response = boiService_underTest.editaBoi(boiRequestDTO, boiId);


        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        verify(loteService).getLoteById(novoLoteId);
        verify(racaService).getRacaByNome(novaRacaStr);

        assertThat(boiArgumentCaptor.getValue()).isEqualTo(expectedBoi);
        assertThat(response).isEqualTo(new BoiResponseDTO(expectedBoi));

    }

    @Test
    @Disabled
    void deleteBoi() {
    }
}