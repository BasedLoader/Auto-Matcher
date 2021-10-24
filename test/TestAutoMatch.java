import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import automatcher.AutoMatcher;

public class TestAutoMatch {
	public static void main(String[] args) throws IOException {
		Path dir = Path.of("run");
		Files.createDirectories(dir);
		Path mcA = dir.resolve("a.jar");
		Path mcB = dir.resolve("b.jar");
		download(mcA, "https://launcher.mojang.com/v1/objects/1cf89c77ed5e72401b869f66410934804f3d6f52/client.jar");
		download(mcB, "https://launcher.mojang.com/v1/objects/fe88ac6c8a0bedc9a48e5c9b48eb0f4dc24ccc79/client.jar");
		Path result = dir.resolve("output.match");
		new AutoMatcher().addInputA(mcA).addInputB(mcB).execute(result);
	}

	private static void download(Path destination, String url) throws IOException {
		if(!Files.exists(destination)) {
			try(InputStream download = new URL(url).openStream()) {
				Files.copy(download, destination);
			}
		}
	}
}
