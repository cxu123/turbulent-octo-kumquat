package control;

import java.util.ArrayList;
import java.util.List;


public class CityListData {

	String DATA[][] = new String [][] {
			/*eg  A , ����*/	
			{"B","������","������","�ɽ��"},
			{"C","�ǿ���","������","����"},
			{"D","������","��ɿ���","�潭��"},
			{"F","�ᶼ��","�����"},
			{"H","�ϴ���"},
			{"J","������","������","��������"},
			{"K","����"},
			{"N","��ƽ��","�ϰ���","�ϴ���"},
			{"P","������","��ˮ��"},
			{"Q","�뽭��","ǭ����"},
			{"R","�ٲ���"},
			{"S","ɳƺ����","ʯ����","˫����"},
			{"T","ͭ����","������"},
			{"W","��ʢ��","������","��Ϫ��","��¡��"},
			{"X","��ɽ��"},
			{"Y","������","�山��","������","������"},
			{"Z","����"},
		
		};
	
	public String GetCityF(int x,int y) {
		return DATA[x][y];
	}
	
	public int GetCityCount() {
		return DATA.length;
	}
	
	public List<String> GetCityOneData(int position) {
		
			String []a = new String[DATA[position].length-1];		//��Ҫ����ǰ�����ĸ
			List<String> b=new ArrayList<String>();
//			List<String> b=new ArrayList<>();
			int cut=0;
			for(int i=1 ;i<DATA[position].length ;i++,cut++){
				a[cut]=DATA[position][i];
				b.add(a[cut]);
			}
			
			return b;
		}
	
	
}
