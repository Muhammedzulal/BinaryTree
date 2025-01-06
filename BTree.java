package Tree;

import java.util.LinkedList;
import java.util.Queue;

public class BTree {
    Node root; // Ağacın kök düğümü

    // Yeni bir ağaç oluşturur. Başlangıçta kök düğüm boş olarak ayarlanır.
    public BTree() {
        root = null;
    }

    // Ağaca veri eklemek için kullanılır.
    // Eğer ağaçta düğüm yoksa yeni bir düğüm oluşturur.
    // Eğer veri mevcut kök düğümden küçükse sol alt ağaçta ekleme yapar.
    // Eğer veri büyükse sağ alt ağaçta ekleme yapar.
    private Node insert(Node root, int data) {
        if (root == null) {
            // Yeni bir düğüm oluştur ve geri döndür
            return new Node(data);
        }
        if (data < root.data) {
            // Veriyi sol alt ağaca ekle
            root.left = insert(root.left, data);
        } else {
            // Veriyi sağ alt ağaca ekle
            root.right = insert(root.right, data);
        }
        return root; // Güncellenmiş kök düğümü döndür
    }

    // ^ Ekleme
    public void add(int data) {// Ağaca yeni bir veri ekler ve kök düğümü günceller.
        root = insert(root, data); // Ağaca veriyi ekler
        System.out.println(data + " Ağaca eklendi"); // Eklenen veriyi yazdır
    }

    // ^ Pre-order Kök-Sol-Sağ
    public void preOrder(Node root) {
        if (root != null) {
            // Önce kökü yazdır
            System.out.print(root.data + " ");
            // Daha sonra sol alt ağacı dolaş
            preOrder(root.left);
            // En son sağ alt ağacı dolaş
            preOrder(root.right);
        }
    }

    // ^ In-order Sol-Kök-Sağ
    public void inOrder(Node root) {
        if (root != null) {
            // Önce sol alt ağacı dolaş
            inOrder(root.left);
            // Daha sonra kökü yazdır
            System.out.print(root.data + " ");
            // En son sağ alt ağacı dolaş
            inOrder(root.right);
        }
    }

    // ^ Post-order Sol-Sağ-Kök
    public void postOrder(Node root) {
        if (root != null) {
            // Önce sol alt ağacı dolaş
            postOrder(root.left);
            // Daha sonra sağ alt ağacı dolaş
            postOrder(root.right);
            // En son kökü yazdır
            System.out.print(root.data + " ");
        }
    }

    // ^Level-order
    public void levelOrder(Node root) {
        if (root == null) {
            System.out.println("Ağaç Boş");
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);// Kuyruğa Kökü ekledik
        while (!queue.isEmpty()) {// Boş olmadığı sürece
            Node current = queue.poll();// çek ve current e ata
            System.out.print(current.data + " ");
            if (current.left != null)// solu varsa ekle
                queue.add(current.left);
            if (current.right != null)// sağı varsa ekle
                queue.add(current.right);

        }
    }

    // ^ Silme
    // Ana silme metodu
    private Node remove(Node root, int x) {
        if (root == null) // Ağaç boşsa veya düğüm bulunamazsa
            return null;

        if (x < root.data) {
            root.left = remove(root.left, x); // Sol alt ağaçta ara
        } else if (x > root.data) {
            root.right = remove(root.right, x); // Sağ alt ağaçta ara
        } else { // Silinecek düğüm bulundu
            // 1. Durum: Yaprak düğüm (hiç çocuğu yok)
            if (root.left == null && root.right == null) {
                return null;
            }
            // 2. Durum: Tek çocuklu düğüm
            else if (root.left == null) {
                return root.right; // Sağ çocuğu döner
            } else if (root.right == null) {
                return root.left; // Sol çocuğu döner
            }
            // 3. Durum: İki çocuklu düğüm
            else {
                // Sağ alt ağaçta en küçük değeri bul
                Node successor = root.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                // Silinecek düğümün verisini successor ile değiştir
                root.data = successor.data;
                // Sağ alt ağaçtan successor'ı sil
                root.right = remove(root.right, successor.data);
            }
        }
        return root; // Güncellenmiş root'u döner
    }

    // Kullanıcı tarafından çağrılan silme metodu
    public void delete(int x) {
        root = remove(root, x);
    }

    // ^ Ağacın eleman sayısı
    public int size(Node root) {
        if (root == null) {
            return 0; // Eğer düğüm boşsa, 0 döndür.
        } else {
            // Sol ve sağ alt ağaçların eleman sayısını hesapla ve kök düğümünü ekle.
            return size(root.left) + size(root.right) + 1;
        }
    }

    // ^Max değer
    public int maxValue(Node root) {
        if (root == null)
            return -1;
        while (root.right != null) {
            root = root.right;
        }
        return root.data;
    }

    // ^Min değer
    public int minValue(Node root) {
        if (root == null)
            return -1;
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    // ^ Ağacın yüksekliği
    public int height(Node root) {
        // Eğer ağaç boşsa yüksekliği 0 döndür
        if (root == null)
            return 0;

        // Sol alt ağacın yüksekliğini hesapla
        int leftHeight = height(root.left);
        // Sağ alt ağacın yüksekliğini hesapla
        int rightHeight = height(root.right);

        // Sol ve sağ yüksekliklerden büyük olanı al, 1 ekle ve döndür
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /*
     * -----10------
     * ----/--\-----
     * ---5----20---
     * --/----/--\--
     * -1----15--25-
     *
     * findHeight(10)
     * ├── findHeight(5)
     * │ ├── findHeight(1) -> 1
     * │ └── findHeight(null) -> 0
     * │ => Math.max(1, 0) + 1 = 2
     * ├── findHeight(20)
     * │ ├── findHeight(15) -> 1
     * │ ├── findHeight(25) -> 1
     * │ => Math.max(1, 1) + 1 = 2
     * => Math.max(2, 2) + 1 = 3
     */

}
