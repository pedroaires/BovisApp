package bovisApp.firstApp.service;

import bovisApp.firstApp.exception.Lote.LoteNaoEncontradoException;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.repository.LoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoteServiceImplTest {
    private LoteService loteService_underTest;

    @Mock
    private LoteRepository loteRepository;

    @BeforeEach
    void setUp(){
        loteService_underTest = new LoteServiceImpl(loteRepository);
    }
    private Long geraIdAleatorio() {
        return (long) (Math.random() * 1000);
    }
    @Test
    void deveRetornarLotePorId(){
        // given
        Long id = geraIdAleatorio();
        Lote loteEsperado = new Lote();
        loteEsperado.setId(id);

        // when
        when(loteRepository.findById(id)).thenReturn(java.util.Optional.of(loteEsperado));
        loteService_underTest.getLoteById(id);

        // then
        verify(loteRepository).findById(id);
        assertThat(loteService_underTest.getLoteById(id)).isEqualTo(loteEsperado);
    }

    @Test
    void naoDeveRetornarLotePorIdInexistente(){
        // given
        Long id = geraIdAleatorio();

        // when
        when(loteRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(null));

        // then
        assertThatThrownBy(() -> loteService_underTest.getLoteById(id))
                .isInstanceOf(LoteNaoEncontradoException.class);
    }

    @Test
    void deveRetornarNovoLoteIdNulo(){
        // given
        Long id = null;

        // when
        Lote loteEsperado = new Lote();
        loteEsperado.setDescricao("Lote criado automaticamente para boi sem lote");
        Lote response = loteService_underTest.getLoteById(id);


        // then
        ArgumentCaptor<Lote> loteArgumentCaptor = ArgumentCaptor.forClass(Lote.class);
        verify(loteRepository).save(loteArgumentCaptor.capture());
        Lote loteCapturado = loteArgumentCaptor.getValue();
        assertThat(loteCapturado).isEqualTo(loteEsperado);
        assertThat(response).isEqualTo(loteEsperado);
    }

    @Test
    void deveRetornarLotes(){
        // given
        List<Lote> lotesEsperados = List.of(new Lote());

        // when
        when(loteRepository.findAll()).thenReturn(lotesEsperados);
        List<Lote> response = loteService_underTest.getLotes();

        // then
        verify(loteRepository).findAll();
        assertThat(response).isEqualTo(lotesEsperados);
    }

    @Test
    void deveRetornarLotesVazio(){
        // given
        List<Lote> lotesEsperados = List.of();

        // when
        when(loteRepository.findAll()).thenReturn(lotesEsperados);
        List<Lote> response = loteService_underTest.getLotes();

        // then
        verify(loteRepository).findAll();
        assertThat(response).isEqualTo(lotesEsperados);
    }
}
