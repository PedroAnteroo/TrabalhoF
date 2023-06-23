Class mesa:

public class Mesa {
    private int numeroMesa;
    private String situacao;
    private int capacidadeMaxima;
    private int garcomId;
    Mesa(int numeroMesa, String situacao, int capacidadeMaxima, int garcomId) {
        this.numeroMesa = numeroMesa;
        this.situacao = situacao;
        this.capacidadeMaxima = capacidadeMaxima;
        this.garcomId = garcomId;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {

        this.situacao = situacao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getGarcomId() {
        return garcomId;
    }

    public void setGarcomId(int garcomId) {
        this.garcomId = garcomId;
    }
}
