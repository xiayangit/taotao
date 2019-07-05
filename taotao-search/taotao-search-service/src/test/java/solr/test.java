package solr;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solr
 * @author xinlian
 *
 */
public class test {


	//新增
	@Test
	public void testAddDoc() throws SolrServerException, IOException {
		SolrServer solrServer = new HttpSolrServer("http://192.168.128.129:8080/solr/collection1"); //连接索引库
		SolrInputDocument doc = new SolrInputDocument();//文档对象
		doc.addField("id", "t02"); //向各个域中插入值
		doc.addField("item_title", "标题2");
		doc.addField("item_price",20);
		solrServer.add(doc); //将文本写入索引库
		solrServer.commit();
	}
	
	
	//删除
	@Test
	public void deleteDoc()  throws SolrServerException, IOException {
		SolrServer solrServer = new HttpSolrServer("http://192.168.128.129:8080/solr/collection1"); //索引库
		solrServer.deleteById("t01");
		solrServer.commit();
	}
	
	//搜索
	@Test
	public void search() throws Exception {
		//连接索引库
		SolrServer solrServer = new HttpSolrServer("http://192.168.128.129:8080/solr/collection1"); 
		//创建查询对象
		SolrQuery query = new SolrQuery();
		//查询关键字
		//可以根据set方法设置不同的参数与值 q:查询条件  fq:过滤条件  sort:排序条件  fl:返回搜索域
		//query.set("q", "手机");
		query.setQuery("手机");
		//设置分页条件
		query.setStart(0);
		query.setRows(10);
		//设置默认搜索域 field
		query.set("df", "item_keywords");
		//设置高亮 true:需要同时指定3个参数
		query.setHighlight(true);
		//设置高亮域
		query.addHighlightField("item_title");
		//设置高亮前后缀
		query.setHighlightSimplePre("<span>");
		query.setHighlightSimplePost("</span>");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList results = response.getResults();
		//取高亮结果 第一层map key为id  第二层为高亮域的名称
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//取总条数
		long numFound = results.getNumFound();
		System.out.println("总条数"+numFound);
		for (SolrDocument item : results) {
			Map<String, List<String>> map = highlighting.get(item.get("id"));
			List<String> list = map.get("item_title");
			String title = "";
			if(list != null && list.size() >0 ) {
				title = list.get(0);
			}else {
				title = (String) item.get("item_title");
			}
			System.out.println(item.get("id"));
			System.out.println(title);
			System.out.println(item.get("item_sell_point"));
			System.out.println(item.get("item_price"));
			System.out.println(item.get("item_image"));
			System.out.println(item.get("item_category_name"));
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}
	}
	
}
