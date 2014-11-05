package jp.level_five.pgcon.building_block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class CubeBuilder {
    public static final String[] FACE_NAMES = {"front", "back", "left", "right", "top", "bottom"};
    
    private LinkedHashMap<String, LinkedList<String>> relation_;
    private List<String> builtCubes_ = new ArrayList<String>();
    
    public CubeBuilder(List<Cube> cubes) {
        Relation relation = new Relation();
        relation_ = relation.create(cubes);
        build();
    }
    
    public List<String> getBuiltCubes() {
        return builtCubes_;
    }
    
    private void build() {
        for (String bottomFace : relation_.keySet()) {
            ArrayList<String> builtCubes = new ArrayList<String>();
            String topFace = getOpositeface(bottomFace);
            builtCubes.add(topFace);
            List<String> relationsToButtom = relation_.get(bottomFace);
            next(relationsToButtom, builtCubes);
        }
    }
    
    private void next(List<String> relationsToButtom, ArrayList<String> builtCubes) {
        if (relationsToButtom != null) {
            sortChildren(relationsToButtom, builtCubes);
        } else {
            compare(builtCubes);
        }
    }
    
    private void compare(ArrayList<String> builtCubes) {
        if (builtCubes_.size() < builtCubes.size()) {
            builtCubes_ = builtCubes;
        }
        builtCubes = null;
    }
    
    private void sortChildren(List<String> relationsToButtom, ArrayList<String> builtCubes) {
        for (String topFace : relationsToButtom) {
            ArrayList<String> copyBuiltCubes = new ArrayList<String>(builtCubes);
            copyBuiltCubes.add(topFace);
            String bottomFace = getOpositeface(topFace);
            List<String> relationToBottom = relation_.get(bottomFace);
            next(relationToBottom, copyBuiltCubes);
        }
    }
    
    public String getOpositeface(String faceID) {
        String opositeFaceID = null;
        String[] split = faceID.split(" ");
        for (int i = 0; i < FACE_NAMES.length; i++) {
            if (split[1].equals((FACE_NAMES[i]))) {
                if (i % 2 == 0) {
                    opositeFaceID = createID(split[0], i + 1);
                } else {
                    opositeFaceID = createID(split[0], i - 1);
                }
            }
        }
        return opositeFaceID;
    }
    
    private String createID(String cubeNumber, int index) {
        return cubeNumber + " " + FACE_NAMES[index];
    }
}