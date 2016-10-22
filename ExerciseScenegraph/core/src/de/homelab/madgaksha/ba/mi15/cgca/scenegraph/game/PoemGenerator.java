package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import com.badlogic.gdx.math.MathUtils;

public enum PoemGenerator {
	LOVE(
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
			new String[] { "hey", "ha", "alas", "oh", "lord", "damn", "god", "well", "please", "beware", "behold" }),
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
					"yes", "maybe" }),
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
			"behold" }),
	SEA(
			new String[] { "The %5 %1 %6 %3s the %1.", "%5, %5 %1s %6 %3 a %5, %5 %1.", "%2 is a %5 %1.", "%9, %2!",
					"1s %4!", "The %1 %4s like a %5 %1.", "%1s %4 like %5 %1s.", "Why does the %1 %4?",
					"%4 %6 like a %5 %1.", "%2, %2, and %2.", "Where is the %5 %1?", "All %1s %3 %5, %5 %1s.",
					"Never %3 a %1.", "%9, a %1 of %2, %3s a %5 %2.", "No %5 %2 ever %4s %6, yet it %3s %1s." },
			new String[] { "sea", "ship", "sail", "wind", "breeze", "wave", "cloud", "mast", "captain", "sailor",
					"shark", "whale", "tuna", "seashell", "pirate", "lad", "girl", "gull", "reef", "shore", "mainland",
					"moon", "sun" },
			new String[] { "adventure", "courage", "endurance", "desolation", "death", "life", "love", "faith",
					"wisdom", "innocence" },
			new String[] { "command", "view", "lead", "pull", "love", "desire", "fight", "moisten" },
			new String[] { "travel", "sail", "wave", "grow", "rise", "fall", "endure", "die" },
			new String[] { "big", "small", "old", "cold", "warm", "sunny", "rainy", "misty", "clear", "stormy", "rough",
					"lively", "dead" },
			new String[] { "swiftly", "calmly", "quietly", "roughly" },
			new String[] {},
			new String[] {},
			new String[] { "o", "oh", "ooh", "ah", "lord", "god", "wow", "golly", "gosh" }),
	CITY(
			new String[] { "The %5 %1 %6 %3s the %1.", "%5, %5 %1s %6 %3 a %5, %5 %1.", "%2 is a %5 %1.",
					"%9 %2, %1s %4!", "The %1 %4s like a %5 %1.", "%1s %4 like %5 %1s.", "Why does the %1 %4?",
					"%4 %6 like a %5 %1.", "%2, %2, and %2.", "Where is the %5 %1?", "All %1s %3 %5, %5 %1s.",
			"Never %3 a %1." },
			new String[] { "street", "sidewalk", "corner", "door", "window", "hood", "slum", "skyscraper", "car",
					"truck", "guy", "girl", "job", "flower", "light", "cigarette", "rain", "jackhammer", "driver",
			"worker" },
			new String[] { "action", "work", "noise", "desolation", "death", "life", "love", "faith", "anger",
			"exhaustion" },
			new String[] { "get", "grab", "shove", "love", "desire", "buy", "sell", "fight", "hustle", "drive" },
			new String[] { "talk", "gab", "walk", "run", "stop", "eat", "grow", "shrink", "shop", "work" },
			new String[] { "big", "small", "old", "fast", "cold", "hot", "dark", "dusty", "grimy", "dry", "rainy",
					"misty", "noisy", "faceless", "dead" },
			new String[] { "quickly", "loudly", "calmly", "quietly", "roughly" },
			new String[] {},
			new String[] {},
			new String[] { "o", "oh", "ooh", "ah", "lord", "god", "damn" }

			),
	SURREAL(
			new String[] { "%1 %3 %4 %7 %1 %8 %9.", "%1 %3 %4 %7 %2 %8 %10.", "%2 %3 %5 %6 %1 %8 %9.",
					"%2 %3 %5 %6 %2 %8 %10.", "%2 %8 churches surrounded by %2 %3 %5, %1 %4 pleading for %2 %10.",
			"%1 %3 %4 of %9 never %7 anything but %2 not very %8 %10?" },
			new String[] { "the", "a", "just a", "one", "about one", "no", "exactly one", "another", "half a", "less than one",
			"that" },
			new String[] { "many", "exceedingly many", "more", "two", "no", "less than four", "a multitude of", "a dozen", "a myriad of",
					"umpteen", "countless", "innumerable", "additional", "some" },
			new String[] { "black", "cruel", "drunken", "handsome", "happy", "kind", "melancholous", "thirsty", "ugly",
					"white", "opaque", "translucent", "nocturnal", "ephemeral", "singular" },
			new String[] { "Zarathustra", "camel", "cat", "dog", "feminist", "flower", "plumber", "poet", "princess",
					"student", "teacher", "Freya", "skull", "butterfly", "siren", "disciple", "Cerberus", "virgin",
			"assasin" },
			new String[] { "Zarathustras", "camels", "cats", "dogs", "feminists", "flowers", "plumbers", "poets",
					"princesses", "students", "teachers", "Freyas", "skulls", "butterflies", "sirens", "disciples",
					"Cerberuses", "virgins", "assasins" },
			new String[] { "split", "mutilate", "freeze", "destroy", "create", "batter", "torment", "tickle", "stroke",
					"harvest", "mummify", "electrocute", "enlighten", "funnel", "swallow", "shield", "crucify", "unlock",
					"nurse", "mistrust", "nullify"},
			new String[] { "splits", "mutilates", "freezes", "destroys", "creates", "batters", "torments", "tickles",
					"strokes", "harvests", "mummifies", "electrocutes", "enlightens", "funnels", "swallows", "shields",
					"crucifies", "unlocks", "nurses", "mistrusts", "nullifies" },
			new String[] { "bellowing", "common", "cruel", "delicious", "drunken", "fair", "narcissistic", "fragile",
					"poor", "putrid", "rare", "rich", "rigid", "snorting", "special", "ugly", "white", "distant",
					"sublime", "subterranean", "floral", "shady", "deterministic" },
			new String[] { "paradise", "neon bulb", "life", "internet", "individual", "fascism", "family", "earth",
					"death", "computer", "candle", "camel", "boss", "worker", "wallet", "student", "buddha", "valhalla",
					"moonlight", "abyss", "lily" },
			new String[] { "paradises", "neon bulbs", "lifes", "internets", "individuals", "fascisms", "families",
					"earths", "deaths", "computers", "candles", "camels", "bosss", "workers", "wallets", "students",
					"buddhas", "valhallas", "moonlights", "abysses", "lilies" });

	private final String[] patterns;
	private final String[][] classWords;

	private PoemGenerator(final String[] patterns, final String[]... classWords) {
		this.patterns = patterns;
		this.classWords = classWords;
	}

	public String generateLine() {
		String pattern = patterns[MathUtils.random(patterns.length - 1)];
		for (int i = classWords.length; i-- > 0;) {
			final String[] words = classWords[i];
			final String rep = "%" + (i + 1);
			while (pattern.indexOf(rep) >= 0)
				pattern = pattern.replaceFirst("%" + (i + 1), words[MathUtils.random(words.length - 1)]);
		}
		if (pattern.length() < 2)
			return pattern;
		return Character.toUpperCase(pattern.charAt(0)) + pattern.substring(1);
	}

	public String[] generateLines(final int n) {
		final String[] res = new String[n];
		for (int i = n; i-- > 0;)
			res[i] = generateLine();
		return res;
	}

	public static String lineFromRandomTheme() {
		return PoemGenerator.values()[MathUtils.random(PoemGenerator.values().length - 1)].generateLine();
	}

	public static void main(final String args[]) {
		for (final String s : PoemGenerator.SURREAL.generateLines(10))
			System.out.println(s);
	}
}
