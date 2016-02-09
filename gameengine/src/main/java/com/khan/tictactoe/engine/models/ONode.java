package com.khan.tictactoe.engine.models;

public class ONode extends Node {
    public ONode(Board currentBoard, int x, int y, int level) {
        super(currentBoard, x, y, ++level);
    }

    private static Value tmpValue;

    @Override
    public Value getValue() {
        if(mValue != null) {
            return mValue;
        }
        Field[][] currentFields = currentBoard.getBoard();
        int maxValue = Integer.MIN_VALUE;
        int wins = 0;
        int count = 0;
        for(int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                if(currentFields[y][x].value == Field.VALUE_UNDEFINED) {
                    tmpValue = checkMove(x, y);
                    currentFields[y][x].value = Field.VALUE_UNDEFINED;
                    maxValue = Math.max(tmpValue.result, maxValue);
                    count++;
                    if (tmpValue.result == -1) wins++;
                }
            }
        }
        mValue = new Value(maxValue, (double) wins / count);
        return mValue;
    }

    @Override
    protected Value checkMove(int pX, int pY) {
        Value win = new Value(-1, 1);
        Value draw = new Value(0, 1);
        Board moveBoard = currentBoard;
        Field[][] fields = moveBoard.getBoard();
        fields[pY][pX].value = Field.VALUE_O;

        State winState = new State(pX, pY, win, new Board(fields));
        State drawState = new State(pX, pY, draw, new Board(fields));

        //Check horizontal
        boolean flag = true;
        for(int x = 0; x < Board.BOARD_WIDTH; x++) {
            if(fields[pY][x].value != Field.VALUE_O) {
                flag = false;
                break;
            }
        }
        if(flag) {
            children.add(winState);
            return win;
        }
        //Check vertical
        flag = true;
        for(int y = 0; y < Board.BOARD_HEIGHT; y++) {
            if(fields[y][pX].value != Field.VALUE_O) {
                flag = false;
                break;
            }
        }
        if(flag) {
            children.add(winState);
            return win;
        }

        //Check diagonals
        if(pX == pY) {
            flag = true;
            for (int x = 0; x < Board.BOARD_WIDTH; x++) { //just using x as diagonal condition
                if (fields[x][x].value != Field.VALUE_O) {//because x=y;
                    flag = false;
                    break;
                }
            }
            if(flag) {
                children.add(winState);
                return win;
            }
        }

        //check other diagonal
        flag = true;
//        int tmpX = pX;
//        int tmpY = pY;
//        for(int x = 0; x < Board.BOARD_WIDTH; x++) { //just using x as diagonal condition
//            tmpX = Math.abs(--tmpX);
//            tmpY = Math.abs(--tmpY);
//            if(fields[tmpY][tmpX].value != Field.VALUE_O) {
//                flag = false;
//                break;
//            }
//        }
        flag = (fields[0][2].value == Field.VALUE_O && fields[1][1].value == Field.VALUE_O && fields[2][0].value == Field.VALUE_O);
        if(flag) {
            children.add(winState);
            return win;
        }

        //Check for all fields filled (draw if there are no win before)
        flag = true;
        for(int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                if(fields[y][x].value == Field.VALUE_UNDEFINED) {
                    flag = false;
                    break;
                }
            }
        }


        if(flag) {
            children.add(drawState);
            return draw;
        }

        XNode xNode = new XNode(new Board(fields), pX, pY, mLevel);
        children.add(xNode);

        return xNode.getValue();
    }
}
