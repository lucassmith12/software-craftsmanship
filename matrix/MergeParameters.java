package matrix;


import java.util.Map;
import java.util.Objects;


final record MergeParameters <K,V> (K index, V x, V y){
	
	public MergeParameters<K,V> setX(Map.Entry<K, V> contents){
		Objects.requireNonNull(contents);
		return new MergeParameters<K,V>(contents.getKey(), contents.getValue(), y);
	}
	
	public MergeParameters<K,V> setY(Map.Entry<K,V> contents){
		Objects.requireNonNull(contents);
		return new MergeParameters<K,V>(contents.getKey(), x, contents.getValue());
	}
	
}
