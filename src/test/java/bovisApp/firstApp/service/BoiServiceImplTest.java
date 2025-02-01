package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.exception.boi.BoiInvalidoException;
import bovisApp.firstApp.exception.boi.BoiJaExisteException;
import bovisApp.firstApp.exception.boi.BoiNaoEncontradoException;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.Raca;
import bovisApp.firstApp.model.enumeration.EstadoBoi;
import bovisApp.firstApp.repository.BoiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private static final String[] RACAS = {"Angus", "Brangus", "Nelore", "Guzera", "Tabapua"};
    private static final String[] ESTADOS = {"na_fazenda", "vendido", "morto"};
    private String geraRacaStrAleatoria(){
        return RACAS[(int) (Math.random() * RACAS.length)];
    }
    private Integer geraNumeroAleatorio(){
        return (int)(Math.random()*1000);
    }

    private String gerarEstadoStrAleatorio(){
        return ESTADOS[(int) (Math.random() * ESTADOS.length)];
    }

    private Long geraIdAleatorio() {
        return (long) (Math.random() * 1000);
    }

    private BoiRequestDTO criaBoiRequestDTO(Integer numero, String raca, Long loteId, String estadoBoi){
        BoiRequestDTO boiRequestDTO = new BoiRequestDTO();
        boiRequestDTO.setNumero(numero);
        boiRequestDTO.setRaca(raca);
        boiRequestDTO.setLoteId(loteId);
        boiRequestDTO.setEstadoBoi(estadoBoi);
        return boiRequestDTO;
    }
    @BeforeEach
    void setUp() {
        boiService_underTest = new BoiServiceImpl(boiRepository, loteService, racaService);
    }

    @Test
    void deveRetornarBois() {
        // given
        List<Boi> bois = new ArrayList<>();
        bois.add(new Boi(1, new Raca("Angus"), new Lote(), EstadoBoi.NA_FAZENDA));
        bois.add(new Boi(2, new Raca("Brangus"), new Lote(), EstadoBoi.VENDIDO));
        bois.add(new Boi(3, new Raca("Nelore"), new Lote(), EstadoBoi.MORTO));
        // when
        when(boiRepository.findAll()).thenReturn(bois);
        List<Boi> response = boiService_underTest.getBois();

        // then
        verify(boiRepository).findAll();
        assertThat(response).isEqualTo(bois);
    }
    @Test
    void deveRetornarBoi() {
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();
        Long boiId = geraIdAleatorio();

        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        Lote lote = new Lote();
        lote.setId(loteId);
        Boi boiEsperado = new Boi(numero, raca, lote, estadoBoi);

        when(boiRepository.findById(boiId)).thenReturn(Optional.of(boiEsperado));
        Boi response = boiService_underTest.getBoiById(boiId);
        verify(boiRepository).findById(boiId);
        assertThat(response).isEqualTo(boiEsperado);
    }

    @Test
    void naoDeveRetornarBoiInexistente() {
        Long boiId = geraIdAleatorio();
        when(boiRepository.findById(boiId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> boiService_underTest.getBoiById(boiId))
                .isInstanceOf(BoiNaoEncontradoException.class);
    }

    @Test
    void deveCadastrarBoiDadosCompletos() {
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);
        Lote lote = new Lote();
        lote.setId(loteId);
        when(loteService.getLoteById(loteId)).thenReturn(lote);

        Boi response = boiService_underTest.cadastraBoi(boiRequestDTO);

        Boi boiEsperado = new Boi(numero, raca, lote, estadoBoi);

        verify(loteService).getLoteById(loteId);
        verify(racaService).getRacaByNome(racaStr);

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boiEsperado);
        assertThat(response).isEqualTo(boiEsperado);
    }

    @Test
    void naoDeveCadastrarBoiExistente(){
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);
        Lote lote = new Lote();
        lote.setId(loteId);
        when(loteService.getLoteById(loteId)).thenReturn(lote);

        Boi boiEsperado = new Boi(numero, raca, lote, estadoBoi);
        when(boiRepository.findByNumeroAndLoteId(numero, loteId))
                .thenReturn(Optional.of(boiEsperado));

        assertThatThrownBy(() -> boiService_underTest.cadastraBoi(boiRequestDTO))
                .isInstanceOf(BoiJaExisteException.class)
                .hasMessage("Boi com mesmo número e lote já cadastrado");
    }

    @Test
    void deveCadastrarBoiLoteNulo() {
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = null;
        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);
        Lote lote = new Lote();
        when(loteService.getLoteById(loteId)).thenReturn(lote);

        Boi response = boiService_underTest.cadastraBoi(boiRequestDTO);
        Boi boiEsperado = new Boi(numero, raca, lote, estadoBoi);

        verify(loteService).getLoteById(loteId);
        verify(racaService).getRacaByNome(racaStr);

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boiEsperado);
        assertThat(response).isEqualTo(boiEsperado);
    }

    @Test
    void naoDeveCadastrarBoiNumeroNulo() {
        Integer numero = null;
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);

        assertThatThrownBy(() -> boiService_underTest.cadastraBoi(boiRequestDTO))
                .isInstanceOf(BoiInvalidoException.class)
                .hasMessage("Número do boi não pode ser nulo");

    }

    @Test
    void naoDeveCadastrarBoiEstadoNulo() {
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = "estado invalido";
        Long loteId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);

        assertThatThrownBy(() -> boiService_underTest.cadastraBoi(boiRequestDTO))
                .isInstanceOf(BoiInvalidoException.class)
                .hasMessage("Estado do boi é inválido. Use 'na_fazenda', 'vendido' ou 'morto'");

    }

    @Test
    void deveCadastrarBoiRacaNula(){
        Integer numero = geraNumeroAleatorio();
        String racaStr = null;
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);
        Lote lote = new Lote();
        lote.setId(loteId);
        when(loteService.getLoteById(loteId)).thenReturn(lote);

        Boi response = boiService_underTest.cadastraBoi(boiRequestDTO);

        Boi boiEsperado = new Boi(numero, raca, lote, estadoBoi);

        verify(racaService).getRacaByNome(racaStr);
        verify(loteService).getLoteById(loteId);

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boiEsperado);
        assertThat(response).isEqualTo(boiEsperado);
    }



    @Test
    void deveEditarBoiDadosCompletos() {
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();
        Long boiId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        when(racaService.getRacaByNome(racaStr)).thenReturn(raca);
        Lote lote = new Lote();
        when(loteService.getLoteById(loteId)).thenReturn(lote);
        Boi boiEsperado = new Boi(numero, raca, lote, estadoBoi);
        when(boiRepository.findById(boiId)).thenReturn(Optional.of(boiEsperado));

        Boi response = boiService_underTest.editaBoi(boiRequestDTO, boiId);

        verify(racaService).getRacaByNome(racaStr);
        verify(loteService).getLoteById(loteId);

        ArgumentCaptor<Boi> boiArgumentCaptor = ArgumentCaptor.forClass(Boi.class);
        verify(boiRepository).save(boiArgumentCaptor.capture());
        assertThat(boiArgumentCaptor.getValue()).isEqualTo(boiEsperado);
        assertThat(response).isEqualTo(boiEsperado);
    }

    @Test
    void naoDeveEditarBoiInexistente(){
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();
        Long boiId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);

        when(boiRepository.findById(boiId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boiService_underTest.editaBoi(boiRequestDTO, boiId))
                .isInstanceOf(BoiNaoEncontradoException.class);
    }

    @Test
    void naoDeveEditarBoiNumeroNulo() {
        Integer numero = null;
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long loteId = geraIdAleatorio();
        Long boiId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);

        when(boiRepository.findById(boiId)).thenReturn(Optional.of(new Boi()));

        assertThatThrownBy(() -> boiService_underTest.editaBoi(boiRequestDTO, boiId))
                .isInstanceOf(BoiInvalidoException.class)
                .hasMessage("Número do boi não pode ser nulo");

    }

    @Test
    void naoDeveEditarBoiEstadoNulo() {
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = "estado invalido";
        Long loteId = geraIdAleatorio();
        Long boiId = geraIdAleatorio();

        BoiRequestDTO boiRequestDTO =
                criaBoiRequestDTO(numero, racaStr, loteId, estadoBoiStr);

        when(boiRepository.findById(boiId)).thenReturn(Optional.of(new Boi()));

        assertThatThrownBy(() -> boiService_underTest.editaBoi(boiRequestDTO, boiId))
                .isInstanceOf(BoiInvalidoException.class)
                .hasMessage("Estado do boi é inválido. Use 'na_fazenda', 'vendido' ou 'morto'");

    }

    @Test
    void deveDeletarBoi() {
        Integer numero = geraNumeroAleatorio();
        String racaStr = geraRacaStrAleatoria();
        String estadoBoiStr = gerarEstadoStrAleatorio();
        Long boiId = geraIdAleatorio();

        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoBoiStr);

        Raca raca = new Raca(racaStr);
        Lote lote = new Lote();
        Boi boiEsperado = new Boi(numero, raca, lote, estadoBoi);

        when(boiRepository.findById(boiId)).thenReturn(Optional.of(boiEsperado));

        Boi response = boiService_underTest.deleteBoi(boiId);

        verify(boiRepository).findById(boiId);
        verify(boiRepository).delete(boiEsperado);
        assertThat(response).isEqualTo(boiEsperado);
    }

    @Test
    void naoDeveDeletarBoiInexistente(){
        Long boiId = geraIdAleatorio();
        when(boiRepository.findById(boiId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> boiService_underTest.deleteBoi(boiId))
                .isInstanceOf(BoiNaoEncontradoException.class);
    }
}