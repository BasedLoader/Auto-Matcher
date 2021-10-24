package automatcher;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import matcher.Matcher;
import matcher.config.Config;
import matcher.config.ProjectConfig;
import matcher.serdes.MatchesIo;
import matcher.type.ClassEnvironment;

public class AutoMatcher {
	final List<Path> inputA = new ArrayList<>(), inputB = new ArrayList<>(), classpathA = new ArrayList<>(), classpathB = new ArrayList<>(),
			sharedClasspath = new ArrayList<>();
	String nonObfClassNamePatternA = "", nonObfClassNamePatternB = "", nonObfMemberNamePatternA = "", nonObfMemberNamePatternB = "";

	public AutoMatcher addInputA(Path input) {
		this.inputA.add(input);
		return this;
	}

	public AutoMatcher addInputB(Path input) {
		this.inputB.add(input);
		return this;
	}

	public AutoMatcher addClasspathA(Path path) {
		this.classpathA.add(path);
		return this;
	}

	public AutoMatcher addClasspathB(Path path) {
		this.classpathB.add(path);
		return this;
	}

	public AutoMatcher addCommonClasspath(Path path) {
		this.sharedClasspath.add(path);
		return this;
	}

	public AutoMatcher setNonObfClassNamePatternA(String nonObfClassNamePatternA) {
		this.nonObfClassNamePatternA = nonObfClassNamePatternA;
		return this;
	}

	public AutoMatcher setNonObfClassNamePatternB(String nonObfClassNamePatternB) {
		this.nonObfClassNamePatternB = nonObfClassNamePatternB;
		return this;
	}

	public AutoMatcher setNonObfMemberNamePatternA(String nonObfMemberNamePatternA) {
		this.nonObfMemberNamePatternA = nonObfMemberNamePatternA;
		return this;
	}

	public AutoMatcher setNonObfMemberNamePatternB(String nonObfMemberNamePatternB) {
		this.nonObfMemberNamePatternB = nonObfMemberNamePatternB;
		return this;
	}

	public ProjectConfig createConfig() {
		return new ProjectConfig(
				this.inputA,
				this.inputB,
				this.classpathA,
				this.classpathB,
				this.sharedClasspath,
				false,
				this.nonObfClassNamePatternA,
				this.nonObfClassNamePatternB,
				this.nonObfMemberNamePatternA,
				this.nonObfMemberNamePatternB);
	}

	public void execute(Path file) throws IOException {
		ProjectConfig config = this.createConfig();
		Config.setProjectConfig(config);
		Matcher.init();
		ClassEnvironment env = new ClassEnvironment();
		Matcher matcher = new Matcher(env);
		matcher.init(config, value -> {});
		matcher.autoMatchAll(value -> {});
		MatchesIo.write(matcher, file);
	}
}
