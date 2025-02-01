package bovisApp.firstApp.service;

import bovisApp.firstApp.model.Medicacao;
import bovisApp.firstApp.model.Pesagem;
import bovisApp.firstApp.repository.PesagemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @BeforeEach
    void setUp(){
        pesagemService_underTest = new PesagemServiceImpl(pesagemRepository);
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
}
