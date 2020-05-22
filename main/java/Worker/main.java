package Worker;

import Worker.workerManager.workerManager;

public class main {
    public static void main(String args[]){
        workerManager wm = new workerManager();
        wm.start();
    }
}
