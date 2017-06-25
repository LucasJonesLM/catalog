package albumList;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import java.util.ArrayList;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import com.google.gson.Gson;

public class Catalog {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] counter = new int[1];
		ArrayList<Album> albums = new ArrayList<Album>();
		albums.add(new Album("Load", "Metallica", "Metal", counter[0]));
		counter[0]++;
		albums.add(new Album("ReLoad", "Metallica", "Metal", counter[0]));
		counter[0]++;
		albums.add(new Album("Barney's Greatest Hits", "Barney", "Children",
				counter[0]));
		port(3000);
//		get("/", (req, res) -> {
//            // convert list of albums into html
//			String html;
//			html = "<!DOCTYPE html><html><head></head><body><ul>";
//			for(Album a:albums){
//				html= html + "<li>Title: "+a.title+"  |  Artist: "+a.artist+"  |  Genre: "+a.genre+"</li>";
//			}
//			html= html + "</ul></body></html>";
//            return html;
//        });
		
		get("/jtwig", (req, res) -> {
            System.out.println("request made");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/jtwig.txt");
            JtwigModel model = JtwigModel.newModel().with("albums", albums);
            return template.render(model);
        });
		get("/json", (req, res) -> {
            System.out.println("request made");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/json.txt");
            JtwigModel model = JtwigModel.newModel();
            return template.render(model);
        });
		post("/gson", (req, res) -> {
			Gson gson = new Gson();
			String albumJson = gson.toJson(albums);
			return albumJson;
        });
		get("/Albums", (req, res) -> {
			System.out.println("request made");
			return "Cool music: " + albums.toString();
		});
		// Show album by ID
		get("/Albums/:id", (req, res) -> {
			System.out.println("request made");
			int id = Integer.parseInt(req.params(":id"));
			int idx = 0;
			for (int i = 0; i < albums.size(); i++) {
				if (albums.get(i).id == id) {
					idx = i;
				}
			}
			return "Selected Album: " + albums.get(idx).toString();
		});
		get("/Albums/", (req, res) -> {
			System.out.println("request made");
			int id = Integer.parseInt(req.queryParams("id"));
			int idx = 0;
			for (int i = 0; i < albums.size(); i++) {
				if (albums.get(i).id == id) {
					idx = i;
				}
			}
			return "Selected Album: " + albums.get(idx).toString();
		});
		// Create new album

		get("/createAlbum", (req, res) -> {
			JtwigTemplate template = JtwigTemplate.classpathTemplate("/create.txt");
			JtwigModel model = JtwigModel.newModel();
			System.out.println("request made");
			counter[0]++;
			albums.add(new Album(req.queryParams("title"),
					req.queryParams("artist"), req.queryParams("genre"),
					counter[0]));

			return template.render(model);

		});
		
		get("/create", (req, res) -> {
			JtwigTemplate template = JtwigTemplate.classpathTemplate("/create.txt");
			JtwigModel model = JtwigModel.newModel();
			System.out.println("request made");
			
			return template.render(model);

		});

		// Remove album

		get("/removeAlbum", (req, res) -> {
			System.out.println("request made");
			
			int id = Integer.parseInt(req.queryParams("id"));
			Album removeAlbum = new Album(null, null, null, id);
			for (int i = 0; i < albums.size(); i++) {
				if (albums.get(i).id == id) {
					removeAlbum = albums.get(i);
				}
			}

			return "Album removed: " + albums.remove(removeAlbum);

		});

	}

}
