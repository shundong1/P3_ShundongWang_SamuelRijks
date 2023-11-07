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

            if(i==ronda){
                System.out.println("Ronda2"+ronda+": ");
            }
            mostrarRecursivament(node.esq,i+1,ronda);
            mostrarRecursivament(node.dret,i+1,ronda);
        }
    }
    // 输入当前轮次的结果
    public void introduirResultats(int ronda, Equip guanyador) {
        introduirResultatsRecursivament(arrel, 1, ronda, guanyador);
    }

    private void introduirResultatsRecursivament(Node<Equip> node, int nivell, int ronda, Equip guanyador) {
        if (node != null) {
            if (nivell == ronda) {
                node.contingut.setPuntuacion(guanyador.getPuntuacion());
            }
            introduirResultatsRecursivament(node.esq, nivell + 1, ronda, guanyador); // 递归左子树
            introduirResultatsRecursivament(node.dret, nivell + 1, ronda, guanyador); // 递归右子树
        }
    }
}
