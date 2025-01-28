package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.lote.LoteRequestDTO;
import bovisApp.firstApp.exception.lote.LoteInvalidoException;
import bovisApp.firstApp.exception.lote.LoteNaoEncontradoException;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.enumeration.EstadoLote;
import bovisApp.firstApp.repository.LoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoteServiceImplTest {
    private LoteService loteService_underTest;

    @Mock
    private LoteRepository loteRepository;

    private Long geraIdAleatorio() {
        return (long) (Math.random() * 1000);
    }
    private Date gerarDataAleatoria() {
        return new Date((long) (Math.random() * 1000000000));
    }

    public static Date adicionarDiasAleatorios(Date data, int maxDias) {
        // Gerar número aleatório de dias entre 0 e maxDias
        int diasAleatorios = (int) (Math.random() * maxDias);

        // Adicionar os dias convertendo para milissegundos
        long milissegundosPorDia = 24L * 60 * 60 * 1000;
        long novaDataEmMillis = data.getTime() + diasAleatorios * milissegundosPorDia;

        return new Date(novaDataEmMillis);
    }


    private EstadoLote geraEstadoLoteAleatorio() {
        EstadoLote[] estados = EstadoLote.values();
        int indiceAleatorio = (int) (Math.random() * estados.length);
        return estados[indiceAleatorio];
    }

    private LoteRequestDTO criaLoteRequestDTO(Date compra, Date venda, String descricao, EstadoLote estado){
        LoteRequestDTO loteRequestDTO = new LoteRequestDTO();
        loteRequestDTO.setDataCompra(compra);
        loteRequestDTO.setDataVenda(venda);
        loteRequestDTO.setDescricao(descricao);
        loteRequestDTO.setEstadoLote(estado);
        return loteRequestDTO;
    }

    @BeforeEach
    void setUp(){
        loteService_underTest = new LoteServiceImpl(loteRepository);
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

    @Test
    void deveCadastrarLoteDadosCompletos(){
        // given
        Date dataCompra = gerarDataAleatoria();
        Date dataVenda = adicionarDiasAleatorios(dataCompra, 10);
        String descricao = "Lote de boi";
        EstadoLote estado = geraEstadoLoteAleatorio();

        LoteRequestDTO loteRequestDTO = criaLoteRequestDTO(dataCompra, dataVenda, descricao, estado);
        Lote loteEsperado = new Lote(dataCompra, dataVenda, estado, descricao);

        // when
        ArgumentCaptor<Lote> loteArgumentCaptor = ArgumentCaptor.forClass(Lote.class);
        Lote response = loteService_underTest.cadastraLote(loteRequestDTO);

        // then
        verify(loteRepository).save(loteArgumentCaptor.capture());
        assertThat(loteArgumentCaptor.getValue()).isEqualTo(loteEsperado);
        assertThat(response.getDataCompra()).isEqualTo(loteEsperado.getDataCompra());
        assertThat(response.getDataVenda()).isEqualTo(loteEsperado.getDataVenda());
        assertThat(response.getDescricao()).isEqualTo(loteEsperado.getDescricao());
        assertThat(response.getEstado()).isEqualTo(loteEsperado.getEstado());
    }

    @Test
    void deveCadastrarLoteDadosNulos(){
        // given
        Date dataCompra = null;
        Date dataVenda = null;
        String descricao = null;
        EstadoLote estado = null;

        LoteRequestDTO loteRequestDTO = criaLoteRequestDTO(dataCompra, dataVenda, descricao, estado);
        Lote loteEsperado = new Lote();

        // when
        ArgumentCaptor<Lote> loteArgumentCaptor = ArgumentCaptor.forClass(Lote.class);
        Lote response = loteService_underTest.cadastraLote(loteRequestDTO);

        // then
        verify(loteRepository).save(loteArgumentCaptor.capture());
        assertThat(loteArgumentCaptor.getValue()).isEqualTo(loteEsperado);
        assertThat(response.getDataCompra()).isEqualTo(loteEsperado.getDataCompra());
        assertThat(response.getDataVenda()).isEqualTo(loteEsperado.getDataVenda());
        assertThat(response.getDescricao()).isEqualTo(loteEsperado.getDescricao());
        assertThat(response.getEstado()).isEqualTo(loteEsperado.getEstado());
    }

    @Test
    void naoDeveCadastrarLoteCompraAposVenda(){
        // given
        Date dataCompra = gerarDataAleatoria();
        Date dataVenda = adicionarDiasAleatorios(dataCompra, -10);
        String descricao = "Lote de boi";
        EstadoLote estado = geraEstadoLoteAleatorio();

        LoteRequestDTO loteRequestDTO = criaLoteRequestDTO(dataCompra, dataVenda, descricao, estado);

        // then
        assertThatThrownBy(() -> loteService_underTest.cadastraLote(loteRequestDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void naoDeveCadastrarLoteVendaSemCompra(){
        // given
        Date dataCompra = null;
        Date dataVenda = gerarDataAleatoria();
        String descricao = "Lote de boi";
        EstadoLote estado = geraEstadoLoteAleatorio();

        LoteRequestDTO loteRequestDTO = criaLoteRequestDTO(dataCompra, dataVenda, descricao, estado);

        // then
        assertThatThrownBy(() -> loteService_underTest.cadastraLote(loteRequestDTO))
                .isInstanceOf(LoteInvalidoException.class);
    }


}
