package control;

import java.util.ArrayList;
import java.util.List;


public class CityListData {

	String DATA[][] = new String [][] {
			/*eg  A , 地区*/	
			{"B","巴南区","北碚区","璧山县"},
			{"C","城口县","长寿区","重庆"},
			{"D","大足县","大渡口区","垫江县"},
			{"F","丰都县","奉节县"},
			{"H","合川区"},
			{"J","江北区","江津区","九龙坡区"},
			{"K","开县"},
			{"N","梁平县","南岸区","南川区"},
			{"P","涪陵区","彭水县"},
			{"Q","綦江县","黔江区"},
			{"R","荣昌县"},
			{"S","沙坪坝区","石柱县","双桥区"},
			{"T","铜梁县","潼南县"},
			{"W","万盛区","万州区","巫溪县","武隆县"},
			{"X","秀山县"},
			{"Y","永川区","渝北区","酉阳县","云阳县"},
			{"Z","忠县"},
		
		};
	
	public String GetCityF(int x,int y) {
		return DATA[x][y];
	}
	
	public int GetCityCount() {
		return DATA.length;
	}
	
	public List<String> GetCityOneData(int position) {
		
			String []a = new String[DATA[position].length-1];		//不要数据前面的字母
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
