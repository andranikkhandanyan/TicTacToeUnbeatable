package com.khan.tictactoe.engine.models;

public class XNode extends Node {
    public XNode(Board currentBoard, int x, int y) {
        super(currentBoard, x, y);
    }

    private static Value tmpValue;

    @Override
    public Value getValue() {
        Field[][] currentFields = currentBoard.getBoard();
        int minValue = Integer.MAX_VALUE;
        int wins = 0;
        int count = 0;
        for(int i = 0; i < Board.BOARD_WIDTH; i++) {
            for (int j = 0; j < Board.BOARD_HEIGHT; j++) {
                if(currentFields[i][j].value == Field.VALUE_UNDEFINED) {
                    tmpValue = checkMove(i, j);
                    minValue = Math.min(tmpValue.result, minValue);
                    count++;
                    if (tmpValue.result == 1) wins++;
                }
            }
        }
        return new Value(minValue, (double) wins / count);
    }

    @Override
    protected Value checkMove(int x, int y) {
        Value win = new Value(1, 1);
        Value draw = new Value(0, 1);
        Board moveBoard = new Board(currentBoard.getBoard());
        Field[][] fields = moveBoard.getBoard();
        fields[x][y].value = Field.VALUE_X;
        //Check horizontal
        boolean flag = true;
        for(int i = 0; i < Board.BOARD_WIDTH; i++) {
            if(fields[i][y].value != Field.VALUE_X) {
                flag = false;
                break;
            }
        }
        if(flag) return win;
        //Check vertical
        flag = true;
        for(int j = 0; j < Board.BOARD_HEIGHT; j++) {
            if(fields[x][j].value != Field.VALUE_X) {
                flag = false;
                break;
            }
        }
        if(flag) return win;

        //Check diagonals
        if(x == y) {
            flag = true;
            for(int i = 0; i < Board.BOARD_WIDTH; i++) {
                if(fields[i][i].value != Field.VALUE_X) {
                    flag = false;
                    break;
                }
            }
            if(flag) return win;
            flag = true;
            for(int j = Board.BOARD_WIDTH - 1; j >= 0; j--) {
                if(fields[j][j].value != Field.VALUE_X) {
                    flag = false;
                    break;
                }
            }
            if(flag) return win;
        }

        //Check for all fields filled (draw if there are no win before)
        flag = true;
        for(int i = 0; i < Board.BOARD_WIDTH; i++) {
            for (int j = 0; j < Board.BOARD_HEIGHT; j++) {
                if(fields[i][j].value == Field.VALUE_UNDEFINED) {
                    flag = false;
                    break;
                }
            }
        }

        if(flag) return draw;

        ONode oNode = new ONode(moveBoard, x, y);
        children.add(oNode);

        return oNode.getValue();
    }
}
