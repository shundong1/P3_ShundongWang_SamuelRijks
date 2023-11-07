package execucio;

import estructura.ArbreBinari;
import estructura.Equip;

public class Main {
    public static void main(String[] args) {

            ArbreBinari arbre = new ArbreBinari();

            // 创建一些队伍节点
            Equip equip1 = new Equip("Equip 1");
            Equip equip2 = new Equip("Equip 2");
            Equip equip3 = new Equip("Equip 3");
            Equip equip4 = new Equip("Equip 4");

            // 插入队伍节点到二叉树
            arbre.inserir(equip1);
            arbre.inserir(equip2);
            arbre.inserir(equip3);
            arbre.inserir(equip4);

            // 显示整个树的内容
            System.out.println("整个树的内容：");
            arbre.mostrar();
        }
    }
