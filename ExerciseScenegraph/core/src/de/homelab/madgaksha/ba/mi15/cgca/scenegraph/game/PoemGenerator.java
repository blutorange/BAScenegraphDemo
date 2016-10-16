package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import com.badlogic.gdx.math.MathUtils;

public enum PoemGenerator {
	LOVE(
			new String[] {"As the %1s are, the %1s %4 %7.", "All the %1s %3 %6, so %5 the %1s.",
					"%9! We %4 the %1 and the %2, why not %5?", "What is the %6 %2 to %7 %3 the %1?", "To %3, we %4. To %3, we %4.",
					"Sometimes %6 and always %6.", "The %6 %1 %7 %3s a %1.", "%7 %6, %1s %7 %3 a %6 %1.", "%2 is a %6 %1 %8 %2 and %2.",
					"%9! %2, the %6 %2.", "%1s %4 the %1 %8 the %6 %2, %7 but %7.", "%1 and %1, %9, %9! %1s like the %2.",
					"%1s %5 and %8 them %2 %5s!", "Only the %1 %5s as a %6 %1.", "%9 %1s...", "What is %2 after all...",
					"We %5, but only for a while,", "When do %1s become %1s?", "Count the %1s, %3 the %2.",
					"Though it's now more %6 and less %6.", "Yet there's %2 %8 the %2s and the %1s.", "%1s -- %6 %1s!",
					"%1s %4 %1s like %6 %1s %5.", "Why did the %1 %3 it, to %3 the %2?", "How does the %1 not %5?",
					"%8 or %8, how %2 %5s on.", "%7, %7, %7.", "%5 %7 like a %6 %1 %8 %2.", "%2, %2, and ever %2.",
					"They never %3 the %1s nor the %1s, with %2.", "Where is the %6 %1, the %6 %2 now?",
					"Where was the %6 %2 then?", "Ever to %3 a %1, it %4 a %1." },
			new String[] { "heart", "face", "eye", "breeze", "wave", "cloud", "lover", "rose", "sky", "moon", "rainbow",
					"day", "gesture", "embrace", "kiss", "lock", "cheek", "grudge", "skin", "smile", "bed", "home" },
			new String[] { "faith", "love", "hate", "fear", "feeling", "time", "size", "color", "desire", "death",
					"life", "meaning", "need", "rebirth", "transformation" },
			new String[] { "make", "see", "feel", "hear", "lead", "pull", "love", "fight", "find", "reveal", "give",
					"drive", "seek" },
			new String[] { "made", "saw", "felt", "heard", "lead", "pulled", "loved", "fought", "found", "revealed",
					"gave", "drove", "sought" },
			new String[] { "run", "bleed", "glow", "live", "wave", "grow", "rise", "breathe", "endure", "die",
					"stumble", "fall", "err", "triumph", "gaze", "breathe", "wink" },
			new String[] { "giddy", "aching", "tender", "hot", "wild", "dark", "luminous", "stormy", "sunny", "clear",
					"blue", "red", "rough", "vigorous", "dead", "wild", "gentle", "nurturing", "painful", "pleasant",
					"pleasurable", "empty", "full", "sweet" },
			new String[] { "gently", "barely", "hardly", "silently", "rapidly", "gamely", "cravenly", "needlessly",
					"devotedly", "wondrously" },
			new String[] { "on", "in", "about", "upon", "within", "between", "under", "above", "after", "before" },
			new String[] { "hey", "ha", "alas", "oh", "lord", "damn", "god", "well", "please", "beware", "behold" }
			),
	SUMMERTIME(
			new String[] { "As the %1s are, the %1s %4 %7.", "All the %1s %3 %6, so %5 the %1s.",
					"%9! We %4 the %1 and the %2, why not %5?", "What is the %6 %2 to %7 %3 the %1?",
					"To %3, we %4. To %3, we %4.", "Sometimes %6 and always %6.", "The %6 %1 %7 %3s a %1.",
					"%7 %6, %1s %7 %3 a %6 %1.", "%2 is a %6 %1 %8 %2 and %2.", "%9! %2, the %6 %2.",
					"%1s %4 the %1 %8 the %6 %2, %7 but %7.", "%1 and %1, %9, %9! %1s like the %2.",
					"%1s %5 and %8 them %2 %5s!", "Only the %1 %5s as a %6 %1.", "%9 %1s...", "What is %2 after all...",
					"We %5, but only for a while,", "When do %1s become %1s?", "Count the %1s, %3 the %2.",
					"Though it's now more %6 and less %6.", "Yet there's %2 %8 the %2s and the %1s.", "%1s -- %6 %1s!",
					"%1s %4 %1s like %6 %1s %5.", "Why did the %1 %3 it, to %3 the %2?", "How does the %1 not %5?",
					"%8 or %8, how %2 %5s on.", "%7, %7, %7.", "%5 %7 like a %6 %1 %8 %2.", "%2, %2, and ever %2.",
					"They never %3 the %1s nor the %1s, with %2.", "Where is the %6 %1, the %6 %2 now?",
					"Where was the %6 %2 then?", "Ever to %3 a %1, it %4 a %1." },
			new String[] { "sun", "beach", "beer", "mint julep", "lawn chair", "bikini", "tank top", "sandal", "cloud",
					"breeze", "day", "night", "firefly", "rainbow", "convertible", "picnic", "party", "patio",
					"mosquito", "storm", "field", "flower", "time", "hour", "minute", "daisy", "sun beam", "daffodil",
					"orange", "lemon", "fan", "poem", "photo", "pal", "cricket", "jewel" },
			new String[] { "love", "sunlight", "sand", "grass", "June", "July", "August", "heat", "wind", "lightning",
					"light", "lethargy", "peace", "distance", "time", "eternity", "sleep", "insomnia", "beauty",
					"uncertainty", "ice", "water", "expanse" },
			new String[] { "make", "see", "feel", "hear", "love", "fight", "find", "reveal", "give", "drive", "seek",
					"paint", "write", "call", "befriend", "pick", "kick", "discover", "remember" },
			new String[] { "made", "saw", "felt", "heard", "pulled", "loved", "fought", "found", "revealed", "gave",
					"drove", "sought", "painted", "wrote", "called", "picked", "discovered", "remembered", "forgot",
			"imitated" },
			new String[] { "stand", "sit", "swim", "run", "bleed", "glow", "live", "wave", "grow", "rise", "breathe",
					"endure", "die", "stumble", "explode", "radiate", "wonder", "wander", "shimmer", "shine", "fall",
					"triumph", "gaze", "breathe", "wink", "hike" },
			new String[] { "ephemeral", "blistering", "searing", "hot", "brisk", "dark", "luminous", "stormy", "sunny",
					"clear", "azure", "magenta", "calloused", "gritty", "sandy", "muddy", "musky", "empty", "full",
					"sweet", "buttery", "paired", "rejuvenated", "veiled", "obscured", "faded", "single", "unique",
			"scary" },
			new String[] { "gently", "barely", "hardly", "silently", "rarely", "gamely", "violently", "nonchalantly",
					"devotedly", "wondrously", "marvelously", "benignly", "malevolently", "voraciously", "temporarily",
					"transparently", "softly" },
			new String[] { "on", "in", "about", "upon", "within", "between", "under", "above", "after", "before" },
			new String[] { "hey", "ha", "alas", "o", "damn", "god", "well", "please", "beware", "behold", "hello", "no",
					"yes", "maybe" }
			),
	PTERODACTYLS(
			new String[] { "As the %1s are, the %1s %4 %7.", "All the %1s %3 %6, so %5 the %1s.",
					"%9! We %4 the %1 and the %2, why not %5?", "What is the %6 %2 to %7 %3 the %1?",
					"To %3, we %4. To %3, we %4.", "Sometimes %6 and always %6.", "The %6 %1 %7 %3s a %1.",
					"%7 %6, %1s %7 %3 a %6 %1.", "%2 is a %6 %1 %8 %2 and %2.", "%9! %2, the %6 %2.",
					"%1s %4 the %1 %8 the %6 %2, %7 but %7.", "%1 and %1, %9, %9! %1s like the %2.",
					"%1s %5 and %8 them %2 %5s!", "Only the %1 %5s as a %6 %1.", "%9 %1s...", "What is %2 after all...",
					"We %5, but only for a while,", "When do %1s become %1s?", "Count the %1s, %3 the %2.",
					"Though it's now more %6 and less %6.", "Yet there's %2 %8 the %2s and the %1s.", "%1s -- %6 %1s!",
					"%1s %4 %1s like %6 %1s %5.", "Why did the %1 %3 it, to %3 the %2?", "How does the %1 not %5?",
					"%8 or %8, how %2 %5s on.", "%7, %7, %7.", "%5 %7 like a %6 %1 %8 %2.", "%2, %2, and ever %2.",
					"They never %3 the %1s nor the %1s, with %2.", "Where is the %6 %1, the %6 %2 now?",
					"Where was the %6 %2 then?", "Ever to %3 a %1, it %4 a %1." },
			new String[] { "reptile", "dinosaur", "bird", "wing", "beak", "screech", "talon", "tree", "eye", "sun",
					"predator", "pterosaur", "sky", "skyline", "jungle", "sun", "breeze", "day", "night" },
			new String[] { "death", "terror", "noise", "desolation", "hunger", "extinction", "beauty", "fear",
					"balance", "nature", "dawn", "dusk", "violence", "majesty", "voracity", "love", "desire",
					"magnitude", "cunning", "destiny", "blood", "flesh", "prey", "heat" },
			new String[] { "ravage", "stalk", "eat", "envision", "smell", "fight", "give", "take", "waste", "terrify",
					"hunt", "radiate", "eclipse" },
			new String[] { "ravaged", "stalked", "ate", "saw", "smelt", "fought", "gave", "took", "wasted", "terrified",
					"hunted", "mimicked", "found", "smelt" },
			new String[] { "fly", "soar", "hide", "exist", "wait", "feast", "preen", "die", "cower", "live", "fall",
					"fail", "win", "glow", "shimmer", "radiate", "cower", "rise", "fall", "alight", "swoop", "linger" },
			new String[] { "Jurassic", "flying", "wild", "calm", "clever", "fossilized", "blue", "yellow", "golden",
					"green", "silvery", "monstrous", "beautiful", "leathery", "majestic", "scaly", "extinct", "pointy",
					"frightening", "sparkly" },
			new String[] { "suddenly", "gracefully", "loudly", "silently", "hastily", "lightly", "heavily", "cunningly",
					"brightly", "darkly", "ominously" },
			new String[] { "on", "in", "about", "upon", "within", "between", "under", "above", "after", "before" },
			new String[] { "o", "alas", "whoosh", "aha", "ay", "kapow", "bang", "snap", "whish", "whoa", "dude", "hey",
			"behold" }
			)
	;

	private final String[] patterns;
	private final String[][] classWords;

	private PoemGenerator(final String[] patterns, final String[]... classWords) {
		this.patterns = patterns;
		this.classWords = classWords;
	}

	public String generateLine() {
		String pattern = patterns[MathUtils.random(patterns.length-1)];
		for (int i = 0; i < classWords.length; ++i) {
			final String[] words = classWords[i];
			final String word = words[MathUtils.random(words.length-1)];
			pattern = pattern.replaceAll("%"+(i+1), word);
		}
		if (pattern.length() < 2) return pattern;
		return Character.toUpperCase(pattern.charAt(0)) + pattern.substring(1);
	}

	public String[] generateLines(final int n) {
		final String[] res = new String[n];
		for (int i = n; i-->0;) res[i] = generateLine();
		return res;
	}

	public static String lineFromRandomTheme() {
		return PoemGenerator.values()[MathUtils.random(PoemGenerator.values().length-1)].generateLine();
	}
}
