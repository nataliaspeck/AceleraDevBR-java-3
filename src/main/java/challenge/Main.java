package challenge;

import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.time.Instant;

public class Main{
	// Quantas nacionalidades (coluna `nationality-15`) diferentes existem no
	// arquivo?
	public int q1() {
		Set<String> nationalities = new HashSet<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] jogador = line.split(",");
				String nationality = jogador[14];
				nationalities.add(nationality);
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return nationalities.size();
	}

	// Quantos clubes (coluna `club-4`) diferentes existem no arquivo?
	// Obs: Existem jogadores sem clube.
	public int q2() {
		Set<String> clubs = new HashSet<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] jogador = line.split(",");
				if (jogador[3].trim().isEmpty()) {
					line = br.readLine();
					continue;
				}
				String club = jogador[3];
				clubs.add(club);
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return clubs.size();
	}

	// Liste o primeiro nome (coluna `full_name-3`) dos 20 primeiros jogadores.
	public List<String> q3() {
		List<String> fullNames = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			for (int i = 0; i < 20; i++) {
				String[] jogador = line.split(",");
				fullNames.add(jogador[2]);
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return fullNames;
	}

	// Quem s�o os top 10 jogadores que possuem as maiores cl�usulas de rescis�o?
	// (utilize as colunas `full_name-3` e `eur_release_clause-19`)
	public List<String> q4() {
		List<Integer> releaseClauses = new ArrayList<Integer>();
		List<String> top10 = new ArrayList<String>();
		List<String> top10names = new ArrayList<String>();
		String[] names = new String[10];
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] jogador = line.split(",");
				if (jogador[18].trim().isEmpty()) {
					line = br.readLine();
					continue;
				}
				float releaseClause = Float.parseFloat(jogador[18]);
				releaseClauses.add((int)releaseClause);
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		Collections.sort(releaseClauses);
		Collections.reverse(releaseClauses);
		
		for (int i = 0; i < 10; i++) {
			top10.add(Integer.toString(releaseClauses.get(i)));
			top10names.add(null);
		}
		
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] jogador = line.split(",");
				if (jogador[18].trim().isEmpty()) {
					line = br.readLine();
					continue;
				}
				String releaseClause = jogador[18].substring(0, jogador[18].length()-2);
				for (String topRC : top10) {
					if (topRC.equals(releaseClause)) {
						names[top10.indexOf(topRC)] = jogador[2];
					}
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		top10names = Arrays.asList(names);
		
		return top10names;
	}

	// Quem s�o os 10 jogadores mais velhos (use como crit�rio de desempate o campo
	// `eur_wage-18`)?
	// (utilize as colunas `full_name-3` e `birth_date-10`)
	public List<String> q5() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> birthDates = new ArrayList<>();
		List<String> top10 = new ArrayList<>();
		List<String> top10names = new ArrayList<>();
		String[] names = new String[10];
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] jogador = line.split(",");
				String birthDate = jogador[8];
				birthDates.add(sdf.parse(birthDate));
				line = br.readLine(); 
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
		Collections.sort(birthDates);
		for (int i = 0; i < 10; i++) {
			String birth = sdf.format(birthDates.get(i));
			top10.add(birth);
		}
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] jogador = line.split(",");
				String birthDate = jogador[8];
				for (String data : top10) {
					if (data.equals(birthDate)) {
						names[top10.indexOf(data)] = jogador[2];
					}
				}
				line = br.readLine(); 
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		top10names = Arrays.asList(names);
		
		return top10names;
	}

	// Conte quantos jogadores existem por idade. Para isso, construa um mapa onde
	// as chaves s�o as idades e os valores a contagem.
	// (utilize a coluna `age-7`)
	public Map<Integer, Integer> q6() {
		Map <Integer, Integer> countAge = new HashMap<>();
		List<Integer> ages = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\codenation\\java-3\\src\\main\\resources\\data.csv"))) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] jogador = line.split(",");
				if (jogador[6].trim().isEmpty()) {
					line = br.readLine();
					continue;
				}
				String age = jogador[6];
				ages.add(Integer.parseInt(age));
				line = br.readLine();
			}
		
			for (Integer idade : ages) {
				if (countAge.keySet().contains(idade)) {
					countAge.put(idade, countAge.get(idade)+1);
				}else {
					countAge.put(idade, 1);
				}
			}
			
		}catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return countAge;
	}

}
