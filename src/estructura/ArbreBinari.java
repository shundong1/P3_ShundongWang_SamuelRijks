package estructura;

public class ArbreBinari {
    private class Node<T> {
        T contingut;
        Node<T> esq, dret;

        Node(T contingut) {
            this(contingut, null, null);
        }

        Node(T contingut, Node<T> esq, Node<T> dret) {
            this.contingut = contingut;
            this.esq = esq;
            this.dret = dret;
        }
    }
    private Node <Equip> arrel;
    public ArbreBinari(){
        arrel= null;
    }
    public void mostrar(int ronda){
        System.out.println("Ronda " + ronda + ":");
        mostrarRecursivament(arrel,1,ronda);
    }

    private void mostrarRecursivament(Node<Equip>node, int i, int ronda) {
        if(node!=null){

            if(i==ronda){}
        }
    }
}
