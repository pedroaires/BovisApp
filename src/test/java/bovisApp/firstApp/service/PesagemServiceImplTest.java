package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.pesagem.PesagemRequestDTO;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Medicacao;
import bovisApp.firstApp.model.Pesagem;
import bovisApp.firstApp.repository.PesagemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PesagemServiceImplTest {
    private PesagemService pesagemService_underTest;

    @Mock
    private PesagemRepository pesagemRepository;

    @Mock
    private BoiService boiService;

    @Mock
    private MedicacaoService medicacaoService;

    private Date gerarDataAleatoria() {
        return new Date((long) (Math.random() * 1000000000));
    }

    private Long geraIdAleatorio() {
        return (long) (Math.random() * 1000);
    }

    private Double geraPesoAleatorio() {
        return Math.random() * 100;
    }

    private Pesagem gerarPesagemAleatoria() {
        return new Pesagem(gerarDataAleatoria(), geraPesoAleatorio(), "descricao", new ArrayList<Medicacao>());
    }

    private Boi gerarBoiAleatorio() {
        Long id = geraIdAleatorio();
        Boi boi = new Boi();
        boi.setId(id);
        return boi;
    }
    @BeforeEach
    void setUp(){
        pesagemService_underTest = new PesagemServiceImpl(pesagemRepository, boiService, medicacaoService);
    }

    @Test
    void deveRetornarPesagens() {
        // given
        List<Pesagem> pesagens = new ArrayList<>();
        pesagens.add(new Pesagem(gerarDataAleatoria(), geraPesoAleatorio(), "descricao1", new ArrayList<Medicacao>()));
        pesagens.add(new Pesagem(gerarDataAleatoria(), geraPesoAleatorio(), "descricao2", new ArrayList<Medicacao>()));
        pesagens.add(new Pesagem(gerarDataAleatoria(), geraPesoAleatorio(), "descricao3", new ArrayList<Medicacao>()));
        pesagens.add(new Pesagem(gerarDataAleatoria(), geraPesoAleatorio(), "descricao4", new ArrayList<Medicacao>()));
        // when
        when(pesagemRepository.findAll()).thenReturn(pesagens);
        List<Pesagem> pesagems = pesagemService_underTest.getPesagens();

        // then
        verify(pesagemRepository).findAll();

        assertThat(pesagems).isEqualTo(pesagens);
    }

    @Test
    void deveRetornarPesagemPorBoiIdValido() {
        // given
        Long boiId = geraIdAleatorio();
        List<Pesagem> pesagensEsperadas = new ArrayList<Pesagem>();
        pesagensEsperadas.add(gerarPesagemAleatoria());
        // when
        when(pesagemRepository.findByBoiId(boiId)).thenReturn(pesagensEsperadas);
        List<Pesagem> pesagensResponse = pesagemService_underTest.getPesagemByBoiId(boiId);

        // then
        verify(pesagemRepository).findByBoiId(boiId);

        assertThat(pesagensResponse).isEqualTo(pesagensEsperadas);
    }

    @Test
    void deveRetornarPesagemPorBoiIdInvalido() {
        // given
        Long boiId = geraIdAleatorio();
        List<Pesagem> pesagensEsperadas = new ArrayList<Pesagem>();

        // when
        when(pesagemRepository.findByBoiId(boiId)).thenReturn(pesagensEsperadas);
        List<Pesagem> pesagensResponse = pesagemService_underTest.getPesagemByBoiId(boiId);

        // then
        verify(pesagemRepository).findByBoiId(boiId);
        assertThat(pesagensResponse).isEqualTo(pesagensEsperadas);
    }

    @Test
    void deveCadastrarPesagemDadosCompletos(){
        // given
        Pesagem pesagemEsperada = gerarPesagemAleatoria();
        String medicacaoStr = "medicacao";
        Boi boi = gerarBoiAleatorio();
        pesagemEsperada.setBoi(boi);
        Medicacao medicacao = new Medicacao(medicacaoStr);
        List<Medicacao> medicacaoList = new ArrayList<Medicacao>();
        medicacaoList.add(medicacao);
        pesagemEsperada.setMedicacoes(medicacaoList);

        // when
        when(medicacaoService.getOrCreateMedicacao(medicacaoStr)).thenReturn(medicacao);
        when(boiService.getBoiById(boi.getId())).thenReturn(boi);
        ArgumentCaptor<Pesagem> pesagemArgumentCaptor = ArgumentCaptor.forClass(Pesagem.class);
        Pesagem response = pesagemService_underTest.cadastraPesagem(
                new PesagemRequestDTO(
                        boi.getId(),
                        pesagemEsperada.getPeso(),
                        pesagemEsperada.getDescricao(),
                        pesagemEsperada.getData(),
                        List.of(medicacaoStr)
                )
        );


        // then
        verify(pesagemRepository).save(pesagemArgumentCaptor.capture());
        assertThat(pesagemArgumentCaptor.getValue()).isEqualTo(pesagemEsperada);
        assertThat(response).isEqualTo(pesagemEsperada);
    }

    @Test
    void naoDeveCadastrarPesagemSemPeso(){
        // given
        Pesagem pesagemEsperada = gerarPesagemAleatoria();
        String medicacaoStr = "medicacao";
        Boi boi = gerarBoiAleatorio();
        pesagemEsperada.setBoi(boi);
        Medicacao medicacao = new Medicacao(medicacaoStr);
        List<Medicacao> medicacaoList = new ArrayList<Medicacao>();
        medicacaoList.add(medicacao);
        pesagemEsperada.setMedicacoes(medicacaoList);

        // when

        // then
        assertThatThrownBy(() -> pesagemService_underTest.cadastraPesagem(
                new PesagemRequestDTO(
                        boi.getId(),
                        null,
                        pesagemEsperada.getDescricao(),
                        pesagemEsperada.getData(),
                        List.of(medicacaoStr)
                )
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void naoDeveCadastrarPesagemSemData(){
        // given
        Pesagem pesagemEsperada = gerarPesagemAleatoria();
        String medicacaoStr = "medicacao";
        Boi boi = gerarBoiAleatorio();
        pesagemEsperada.setBoi(boi);
        Medicacao medicacao = new Medicacao(medicacaoStr);
        List<Medicacao> medicacaoList = new ArrayList<Medicacao>();
        medicacaoList.add(medicacao);
        pesagemEsperada.setMedicacoes(medicacaoList);

        // when

        // then
        assertThatThrownBy(() -> pesagemService_underTest.cadastraPesagem(
                new PesagemRequestDTO(
                        boi.getId(),
                        pesagemEsperada.getPeso(),
                        pesagemEsperada.getDescricao(),
                        null,
                        List.of(medicacaoStr)
                )
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void naoDeveCadastrarPesagemSemBoiId(){
        // given
        Pesagem pesagemEsperada = gerarPesagemAleatoria();
        String medicacaoStr = "medicacao";
        Boi boi = gerarBoiAleatorio();
        pesagemEsperada.setBoi(boi);
        Medicacao medicacao = new Medicacao(medicacaoStr);
        List<Medicacao> medicacaoList = new ArrayList<Medicacao>();
        medicacaoList.add(medicacao);
        pesagemEsperada.setMedicacoes(medicacaoList);

        // when

        // then
        assertThatThrownBy(() -> pesagemService_underTest.cadastraPesagem(
                new PesagemRequestDTO(
                        null,
                        pesagemEsperada.getPeso(),
                        pesagemEsperada.getDescricao(),
                        pesagemEsperada.getData(),
                        List.of(medicacaoStr)
                )
        )).isInstanceOf(IllegalArgumentException.class);
    }
}
