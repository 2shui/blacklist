package com.blacklist.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.blacklist.config.WebConfig;

/**
 * 文章全文索引
 * 
 * */
public class LuceneIKUtil {
	private Logger log = LoggerFactory.getLogger(LuceneIKUtil.class);
	private static Directory directory;
	private static Analyzer analyzer;
	
	private static class LuceneIKUtilHolder{
		private static LuceneIKUtil lucene = new LuceneIKUtil();
	}
	
	public static LuceneIKUtil getInstance() {
		return LuceneIKUtilHolder.lucene;
	}
	
	/**
	 * 创建全量索引
	 * 
	 * @param list
	 *            需要创建的索引
	 * @param peremptory
	 *            是否重建索引
	 * @throws IOException
	 * 
	 * */
	public void createIndex(List<Document> list, boolean peremptory)
			throws Exception {
		IndexWriterConfig writerConf = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, writerConf);
		if (peremptory) {
			writer.deleteAll();
		}
		for (Document doc : list) {
			writer.addDocument(doc);
		}
		writer.close();
	}
	
	/**
	 * 更新缓存
	 * 
	 * @throws IOException
	 * */
	public void updateIndex(Long id, Document doc) throws Exception {
		IndexWriterConfig writerConf = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, writerConf);
		Term term = new Term("id", String.valueOf(id));
		writer.updateDocument(term, doc);
		writer.close();
	}
	
	/**
	 * 删除缓存
	 * 
	 * @throws IOException
	 * */
	public void deleteIndex(Long id) throws Exception {
		IndexWriterConfig writerConf = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, writerConf);
		Term term = new Term("id", String.valueOf(id));
		writer.deleteDocuments(term);
		writer.close();
	}
	
	/**
	 * 多field全文检索
	 * @param
	 * 
	 * @throws IOException, ParseException
	 * */
	public List<Document> search(String[] fields, String key, int num) throws IOException, ParseException {
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
		Query query = parser.parse(QueryParser.escape(key));
		TopDocs docs = searcher.search(query, num);
		if(docs.totalHits < 1) {
			indexReader.close();
			return Arrays.asList();
		}
		List<Document> result = new ArrayList<Document>();
		ScoreDoc[] hits = docs.scoreDocs;
		for(ScoreDoc doc:hits) {
			Document document = searcher.doc(doc.doc);
			result.add(document);
		}
		indexReader.close();
		return result;
	}
	
	private LuceneIKUtil() {
		try {
			directory = FSDirectory.open(new File(WebConfig.path).toPath());
			analyzer = new IKAnalyzer();
		} catch (IOException e) {
			log.error("init lucene error:{}", e.getMessage());
			e.printStackTrace();
		}
	}
	
}
