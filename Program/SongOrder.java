import java.util.ArrayList;

public class SongOrder {
    public LineUp submissions;
    private Contestant c;
    


    public SongOrder (LineUp submissions){
        this.submissions = submissions;
        sorter();
    }

    private void sorter() {
        lowToHigh();
        stockOnce();
        submissions.saveList();
    }

    private void lowToHigh() {
        for (int i = 0; i < submissions.stagePool.size(); i++) {
            for (int j = 0; j < submissions.stagePool.size() - 1; j++) {
                if (submissions.stagePool.get(j).importance > submissions.stagePool.get(j+1).importance) {
                    c = submissions.stagePool.get(j);
                    submissions.stagePool.set(j, submissions.stagePool.get(j+1));
                    submissions.stagePool.set(j+1, c);
                }
            }
        }
    }

    private void stockOnce() {
        ArrayList<Contestant> newer = new ArrayList<>();
        int size = submissions.stagePool.size();
        int mid = size / 2;

        for (int i = 0; i < mid; i++) {
            newer.add(submissions.stagePool.get(mid + i));
            newer.add(submissions.stagePool.get(i));
        }

        if (size % 2 != 0) {
            newer.add(submissions.stagePool.get(size - 1));
        }

        submissions.stagePool.clear();
        submissions.stagePool.addAll(newer);
    }

}
