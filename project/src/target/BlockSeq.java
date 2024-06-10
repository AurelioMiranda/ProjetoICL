package target;

import interpreter.Env;
import types.Type;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockSeq {
    List<Frame> frames;
    Frame currFrame;
    BasicBlock block;
    public CompEnv env;

    public BlockSeq(CompEnv env) {
        this.frames = new ArrayList<>();
        this.env = env;
    }

    public Pair<Frame, CompEnv> beginScope(int nFields) {
        Frame newFrame = new Frame(nFields);
        newFrame.setPrev(currFrame);
        currFrame = newFrame;
        frames.add(currFrame);
        return new Pair<>(currFrame, env);
    }

    public void advanceToFrame(Frame f, CompEnv env) {
        currFrame = f;
        this.env = env;
    }

    public void endScope(Frame f, CompEnv env) {
        if (!frames.isEmpty() && frames.contains(f)) {
            frames.remove(f);
            currFrame = f.getPrev();
            this.env = env;
        }
    }

    public void addInstruction(Instruction i) {
        if (block != null) {
            block.addInstruction(i);
        } else {
            throw new IllegalStateException(" ");
        }
    }

    public void fetch(String id, Type t) {
        if (currFrame != null) {
        } else {
            throw new IllegalStateException(" ");
        }
    }
}