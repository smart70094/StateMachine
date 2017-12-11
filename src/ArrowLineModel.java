import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArrowLineModel {
	//起點與終點座標
	double sx,sy,ex,ey;
	//拖移時記錄的坐標
	double orgSceneX=0;
	double orgSceneY=0;
	double orgTranslateX=0;
	double orgTranslateY=0;

	public ArrowLineModel(double sx,double sy,double ex,double ey) {
		this.sx=sx;
		this.sy=sy;
		this.ex=ex;
		this.ey=ey;
	}
	
	void draggedMoveFrom(double mx,double my,double ox,double oy) {
		orgSceneX = mx;
	    orgSceneY = my;
	    orgTranslateX = ox;
	    orgTranslateY = oy;   
	}
	double[] draggedMoveTo(double mx,double my) {
		double offsetX = mx - orgSceneX;
	    double offsetY = my - orgSceneY;
	    double newTranslateX = orgTranslateX + offsetX;
	    double newTranslateY = orgTranslateY + offsetY;
	    double[] point= {newTranslateX,newTranslateY};
	    return point;
	}
	
	double[] repaintArrow(double x1, double y1, double x2, double y2) {
		double H = 10 ; // 箭頭高度
		double L = 7 ; // 底邊的一半
		int x3 = 0 ;
		int y3 = 0 ;
		int x4 = 0 ;
		int y4 = 0 ;
		double awrad = Math.atan(L / H); // 箭頭角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭頭的長度
		double [] arrXY_1 = rotateVec(x2 - x1, y2 - y1, awrad, true , arraow_len);
		double [] arrXY_2 = rotateVec(x2 - x1, y2 - y1, - awrad, true , arraow_len);
		double x_3 = x2 - arrXY_1[ 0 ]; // (x3,y3)是第一端點
		double y_3 = y2 - arrXY_1[ 1 ];
		double x_4 = x2 - arrXY_2[ 0 ]; // (x4,y4)是第二端點
		double y_4 = y2 - arrXY_2[ 1 ];

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		double point[]= {x3,y3,x4,y4};
		return point;
	}
	public double [] rotateVec( double e, double f, double ang, boolean isChLen,double newLen) {
			double mathstr[] = new double [ 2 ];
			// 向量旋轉函數，參數含義分別是x分量、y分量、旋轉角、是否改變長度、新長度
			double vx = e * Math.cos(ang) - f * Math.sin(ang);
			double vy = e * Math.sin(ang) + f * Math.cos(ang);
			if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[ 0 ] = vx;
			mathstr[ 1 ] = vy;
			}
			return mathstr;
	}

}
