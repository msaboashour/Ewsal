package iti.gp.navigation;

import java.math.BigDecimal;

import android.content.Context;

public class Position {

	private double xPos;
	private double yPos;
	private Context context;
	APScanHelper myHelper;
	
	public Position(Context cxt) {
		context = cxt;
		myHelper = new APScanHelper(context);
	}
	
	public double getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public void getCurrentPosition() {

		myHelper.startWifiWork();

		if(myHelper.APsScaned.size() >= 3)
		{
			float x1 = (float) (myHelper.APsScaned.get(0).getxCordinate());
			float x2 = (float) (myHelper.APsScaned.get(1).getxCordinate());
			float x3 = (float) (myHelper.APsScaned.get(2).getxCordinate());
			
			float y1 = (float) (myHelper.APsScaned.get(0).getyCordinate());
			float y2 = (float) (myHelper.APsScaned.get(1).getyCordinate());
			float y3 = (float) (myHelper.APsScaned.get(2).getyCordinate());
			
			float dist1 = (float) (myHelper.APsScaned.get(0).getDistance() /2.54);
			float dist2 = (float) (myHelper.APsScaned.get(1).getDistance() /2.54);
			float dist3 = (float) (myHelper.APsScaned.get(2).getDistance() /2.54);
		

			float r1= (dist1*dist1)-(x1*x1)-(y1*y1);
			float r2= (dist2*dist2)-(x2*x2)-(y2*y2);
			float r3= (dist3*dist3)-(x3*x3)-(y3*y3);
			
			r1 = (float) round(r1, 3, BigDecimal.ROUND_HALF_UP);
			r2 = (float) round(r2, 3, BigDecimal.ROUND_HALF_UP);
			r3 = (float) round(r3, 3, BigDecimal.ROUND_HALF_UP);
			
			
	        float[][] lhsMatrix = {{ 1, x1*-2, y1*-2}, 
	        						{ 1, x2*-2, y2*-2},
	        						{ 1, x3*-2, y3*-2}
	        					   };
	        
	        float[][] rhsMatrix = {{r1}, 
	        						{r2}, 
	        						{r3}, 
	        						};
	        
			MATRIX lhs = new MATRIX(lhsMatrix);
			MATRIX rhs = new MATRIX(rhsMatrix);
			
			MATRIX lhs_rev =lhs.inverse();
			MATRIX res=	lhs_rev.times(rhs);
						
			if(res.getData()[1][0] < 0)
				xPos = (res.getData()[1][0])*(-160);
			else
				xPos = (res.getData()[1][0])*(160);

			if(res.getData()[2][0] < 0)
				yPos =  (res.getData()[2][0])*(-160);
			else
				yPos =  (res.getData()[2][0])*(160);
		}
		else
		{
			xPos = 0;
			yPos = 0;
		}		
	}	
	
	
	public static double round(double unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.doubleValue();
	}
	
}
