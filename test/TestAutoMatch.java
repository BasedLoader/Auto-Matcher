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
		download(mcA, "https://launcher.mojang.com/v1/objects/a16d67e5807f57fc4e550299cf20226194497dc2/server.jar");
		download(mcB, "https://launcher.mojang.com/v1/objects/dd9ca1bdc855535cd7ce0565f02285ad4d6d1ae5/server.jar");
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
