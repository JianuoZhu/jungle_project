package controller;

import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import java.io.*;
import java.util.ArrayList;

public class MyObjectOutputStream extends ObjectOutputStream {

             protected MyObjectOutputStream() throws IOException, SecurityException {
                super();
             }

             @Override
             protected void writeStreamHeader() throws IOException {

             }

             public MyObjectOutputStream(OutputStream o) throws IOException{
                 super(o);
             }
 }