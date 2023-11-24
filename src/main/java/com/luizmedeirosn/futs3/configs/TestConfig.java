package com.luizmedeirosn.futs3.configs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PositionParameterRepository positionParameterRepository;

    @Autowired
    private PlayerParameterRepository playerParameterRepository;

    private Random random = new Random();

    @Override
    public void run(String... args) throws Exception {

        GameMode gm1 = new GameMode( "4-3-3 (ofensivo)", "O 4-3-3 é amplamente difundido por sua boa distribuição de espaço de jogo. Além disso, é uma formação extremamente flexível desde as fases iniciais da ação ofensiva da saída de bola." );
        GameMode gm2 = new GameMode( "3-1-4-2", "Essa formação trata-se de uma variação do sistema 3-5-2, contudo, aqui o foco é manter a bola afastada do seu gol e concentrada no meio-campo do time adversário." );
        GameMode gm3 = new GameMode( "3-4-1-2", "O 3-4-1-2 é um sistema bastante flexível, e pode se adequar facilmente em manter a bola no meio. Ao mesmo tempo, em que cria oportunidades para seus meio-campistas. Porém, por essa formação ser mais avançada, existe uma possibilidade de redução da profundidade e assim, deixará muito mais fluido no terço atacante em campo." );
        GameMode gm4 = new GameMode( "4-2-3-1 (aberto)", "Essa formação é uma das mais utilizadas por jogadores iniciantes e também por experientes, pois tem o objetivo de espalhar o jogo o máximo possível. De modo a, proporcionar aos meio-campistas oportunidades de criar vantagens para os, alas, o armador e o atacante." );
        GameMode gm5 = new GameMode( "4-3-2-1", "Esse sistema possui uma defesa equilibrada, em simultâneo, as pontas formam um ataque em trio com o centroavente e os meia-centrais encarregam-se da organização do jogo do meio de campo." );
        GameMode gm6 = new GameMode( "4-2-2-2", "Trata-se de uma ótima tática que desenvolve a utilidade das jogadas inteligentes do seu time. Ou seja, é a melhor para os jogadores que pensam fora da caixa. E a razão disso, é por dois motivos: largura e profundidade." );
        GameMode gm7 = new GameMode( "3-5-2", "Esse sistema oferece uma capacidade defensiva sólida, além de conseguir espaço para os 2 jogadores na linha de frente receberem bons passes e assim, conseguir o controle do meio de campo." );
        GameMode gm8 = new GameMode( "4-1-2-1-2", "Destaca-se devido à sua formação estreita, em que seus 2 atacantes e seu meia-ofensivo estarão contra os zagueiros do time adversário, na maioria dos cenários. Proporcionando a você muitas oportunidades de ultrapassar a defesa oponente." );
        GameMode gm9 = new GameMode( "4-4-2", "Uma clássica e equilibrada formação, pois permite uma grande variação de posicionamento. Aqui, você terá 4 jogadores na defesa, 4 no meio de campo e 2 atacantes. E torna-se um esquema difícil de enfrentar, visto que você pode usar seus atacantes, as alas ou até mesmo seu meia-central para lançar sequencias de ataque." );

        gameModeRepository.saveAll (
            Arrays.asList (
                gm1, gm2, gm3 ,gm4, gm5, gm6, gm7, gm8, gm9
            )
        );
        
        Position pos1 = new Position( "GOLEIRO", "Defensor do gol, responsável por proteger a meta e realizar defesas fundamentais para a equipe." );

        Position pos2 = new Position( "LATERAL DIREITO", "Contribui para a defesa e apoia o ataque, fornecendo assistência aos atacantes com avanços pelo lado direito do campo." );

        Position pos3 = new Position( "ZAGUEIRO DESTRO", "Encarregado da marcação sólida e da proteção do gol, ocasionalmente atuando como uma surpresa tática." );

        Position pos4 = new Position( "ZAGUEIRO CANHOTO", "Similar ao zagueiro destro, responsável pela defesa e também podendo surpreender estrategicamente." );

        Position pos5 = new Position( "LATERAL ESQUERDO", "Desempenha papel defensivo e ofensivo, ajudando na defesa e prestando assistência aos atacantes pelo lado esquerdo do campo" );

        Position pos6 = new Position( "TERCEIRO ZAGUEIRO", "Zagueiro que atua como líbero, oferecendo suporte adicional à defesa em momentos estratégicos." );

        Position pos7 = new Position( "PRIMEIRO VOLANTE", "Encarregado da marcação e da transição defesa-ataque, desempenhando um papel vital na distribuição do jogo." );

        Position pos8 = new Position( "SEGUNDO VOLANTE", "Auxilia o primeiro volante, equilibrando o meio de campo e contribuindo tanto na defesa quanto no ataque." );

        Position pos9 = new Position( "MEIA DIREITO", "Focado na criação de jogadas no meio do campo, buscando oportunidades de ataque pelo lado direito." );

        Position pos10 = new Position( "MEIA ESQUERDO", "Similar ao meia direito, concentra-se na criação de jogadas, explorando oportunidades de ataque pelo lado esquerdo." );

        Position pos11 = new Position( "MEIA-ATACANTE", "Atua tanto na criação quanto na finalização das jogadas, sendo uma peça-chave na articulação do jogo." );

        Position pos12 = new Position( "PONTA DIREIRA", "Avança pelas laterais direitas, cruzando bolas na área e contribuindo com o ataque do time." );
        
        Position pos13 = new Position( "PONTA ESQUERDA", "Similar à ponta direita, avança pelas laterais esquerdas, cruzando bolas na área e participando do jogo ofensivo." );

        Position pos14 = new Position( "ATACANTE", "Encarregado de concluir jogadas, seja realizando passes decisivos ou marcando gols para a equipe." );

        Position pos15 = new Position( "CENTROAVANTE", "Jogador que atua próximo à área adversária, responsável por finalizar jogadas com gols, sendo a referência principal do ataque." );

        positionRepository.saveAll (
            Arrays.asList (
                pos1, pos2, pos3 ,pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13, pos14, pos15
            )
        );

        gm1.getPositions().addAll(Arrays.asList(pos5, pos11, pos14));
        gameModeRepository.save(gm1);

        Parameter prmt1 = new Parameter(    "DEFESA DE FALTA",                  null );
        Parameter prmt2 = new Parameter(    "GOL NO PRIMEIRO TEMPO",            null );
        Parameter prmt3 = new Parameter(    "GOL NO SEGUNDO TEMPO",             null );
        Parameter prmt4 = new Parameter(    "CHUTE LATERAL",                    null );
        Parameter prmt5 = new Parameter(    "GOL DE CABEÇA",                    null );
        Parameter prmt6 = new Parameter(    "GOL DE PÊNALTI",                   null );
        Parameter prmt7 = new Parameter(    "GOL DE FALTA",                     null );
        Parameter prmt8 = new Parameter(    "ARREMESSO LATERAL",                null );
        Parameter prmt9 = new Parameter(    "ESCANTEIO",                        null );
        Parameter prmt10 = new Parameter(   "CHUTE DE LONGA DISTÂNCIA",         null );
        Parameter prmt11 = new Parameter(   "CHUTE DE CURTA DISTÂNCIA",         null );
        Parameter prmt12 = new Parameter(   "RESISTÊNCIA",                      null );
        Parameter prmt13 = new Parameter(   "MÉDIA DE DISTÂNCIA PERCORRIDA",    null );
        Parameter prmt14 = new Parameter(   "POTÊNCIA DO CHUTE",                null );
        Parameter prmt15 = new Parameter(   "ACERTO DE PASSE",                  null );
        Parameter prmt16 = new Parameter(   "DESARME",                          null );
        Parameter prmt17 = new Parameter(   "ASSISTÊNCIA",                      null );
        Parameter prmt18 = new Parameter(   "MÉDIA DE FALTAS",                  null );
        Parameter prmt19 = new Parameter(   "AGILIDADE",                        null );
        Parameter prmt20 = new Parameter(   "ACELERAÇÃO",                       null );
        Parameter prmt21 = new Parameter(   "CONDUÇÃO",                         null );
        Parameter prmt22 = new Parameter(   "CONTROLE DE BOLA",                 null );
        Parameter prmt23 = new Parameter(   "FRIEZA",                           null );
        Parameter prmt24 = new Parameter(   "VELOCIDADE",                       null );
        Parameter prmt25 = new Parameter(   "FINALIZAÇÃO",                      null );
        

        parameterRepository.saveAll (
            Arrays.asList (
                prmt1, prmt2, prmt3 ,prmt4, prmt5, prmt6, prmt7, prmt8, prmt9, prmt10, prmt11, prmt12, prmt13, prmt14, prmt15, prmt16, prmt17, prmt18, prmt19, prmt20, prmt21, prmt22, prmt23, prmt24, prmt25
            )
        );

        /*--!> OS JOGADORES ESTÃO ESCALADOS COM POSIÇÕES NO GERAL PARA FACILITAR A MOCKAGEM E PUXAR O RANKING, MAS NÃO SÃO DADOS REAIS */ 
        /* GOLEIROS */
        final String sportRecife = "Sport Recife";
        Player player1 = new Player( "Denis",   null, null, sportRecife, pos1 );
        Player player2 = new Player( "Denival", 23,   203,  sportRecife, pos1 );
        Player player3 = new Player( "Renan",   null, null, sportRecife, pos1 );
        Player player4 = new Player( "Jordan",  null, null, sportRecife, pos1 );
        Player player5 = new Player( "Adriano", null, null, sportRecife, pos1 );

        /* LATERAL ESQUERDO */ 
        Player player6 = new Player( "Roberto Rosales", null, null, sportRecife, pos5 );
        Player player7 = new Player( "Everton",         23, 171,    sportRecife, pos5 );
        Player player8 = new Player( "Felipinho",       19, 182,    sportRecife, pos5 );
        Player player9 = new Player( "Igor Cariús",     null, null, sportRecife, pos5 );
        Player player10 = new Player( "Nathan",         null, null, sportRecife, pos5 );
        Player player11 = new Player( "Eduardo",        null, null, sportRecife, pos5 );
        Player player12 = new Player( "Victor Gabriel", null, null, sportRecife, pos5 );

        // ZAGUEIRO DESTRO
        Player player13 = new Player( "Alisson Cassiano",   null, null, sportRecife, pos3 );
        Player player14 = new Player( "Renzo",              22, 184,    sportRecife, pos3 );
        Player player15 = new Player( "Rafael Thyere",      30, 189,    sportRecife, pos3 );
        Player player16 = new Player( "Sabino",             27, 189,    sportRecife, pos3 );
        Player player17 = new Player( "Chico",              25, 188,    sportRecife, pos3 );
        Player player18 = new Player( "Matheus Baraka",     null, null, sportRecife, pos3 );


        /* PRIMEIRO VOLANTE */
        Player player19 = new Player("Felipe",          null, null, sportRecife, pos7 );
        Player player20 = new Player("Ronaldo",         29, 172,    sportRecife, pos7 );
        Player player21 = new Player("Fabinho",         null, null, sportRecife, pos7 );
        Player player22 = new Player("Pedro Martins",   23, 175,    sportRecife, pos7 );
        Player player23 = new Player("Fábio Matheus",   null, null, sportRecife, pos7 );
        Player player24 = new Player("Lucas André",     null, null, sportRecife, pos7 );

        /* MEIA DIREITO */
        Player player25 = new Player("Alan Ruiz",       null, null, sportRecife, pos9 );
        Player player26 = new Player("Jorginho",        null, null, sportRecife, pos9 );
        Player player27 = new Player("Juan Xavier",     null, null, sportRecife, pos9 );
        
        /* ATACANTES */
        Player player28 = new Player("Michael Lima",        null, null, sportRecife, pos14 );
        Player player29 = new Player("Peglow",              null, null, sportRecife, pos14 );
        Player player30 = new Player("Diego Souza",         null, null, sportRecife, pos14 );
        Player player31 = new Player("Vagner Love",         39, 172,    sportRecife, pos14 );
        Player player32 = new Player("Edinho",              null, null, sportRecife, pos14 );
        Player player33 = new Player("Edison Negueba",      null, null, sportRecife, pos14 );
        Player player34 = new Player("Facundo Labandeira",  27, 173,    sportRecife, pos14 );
        Player player35 = new Player("Gean Carlos",         null, null, sportRecife, pos14 );
        Player player36 = new Player("Wanderson",           35, 167, sportRecife, pos14 );
        Player player37 = new Player("Gabriel Santos",      null, null, sportRecife, pos14 );
        Player player38 = new Player("Fabrício Daniel",     null, null, sportRecife, pos14 );
        
        List<Player> players = new ArrayList<>(
            Arrays.asList (
                player1, player2, player3 ,player4, player5, player6, player7, player8, player9, player10, player11, player12, player13, player14, player15, player16, player17, player18, player19, player20, player21, player22, player23, player24, player25, player26, player27, player28, player29, player30, player31, player32, player33, player34, player35, player36, player37, player38
            )
        );
        playerRepository.saveAll(players);


        
        final String PICTURES_FOLDER_PATH = "/home/luizmedeirosn/Desktop/futS3-files/futS3-backend/src/main/java/com/luizmedeirosn/futs3/configs/data/sport-pictures";
        File picturesFolder = new File(PICTURES_FOLDER_PATH);
        File[] picturesArray = picturesFolder.listFiles(File::isFile);

        List<File> picturesList = new ArrayList<>(Arrays.asList(picturesArray));
        picturesList.sort((f1, f2) -> Integer.parseInt(f1.getName().split("-")[0]) - Integer.parseInt(f2.getName().split("-")[0]));


        for (int i = 0; i < 38; i++) {
            Player player = players.get(i);
            File pictureFile = picturesList.get(i);
            byte[] pictureData = Files.readAllBytes(Paths.get(pictureFile.getAbsolutePath()));

            PlayerPicture playerPicture = new PlayerPicture(
                pictureFile.getName(), "image/webp", pictureData, player
            );

            player.setPlayerPicture(playerPicture);
            playerRepository.save(player);
        }



        PositionParameter pp1 = new PositionParameter(  pos5, prmt14, 25 );
        PositionParameter pp2 = new PositionParameter(  pos5, prmt15, 25 );
        PositionParameter pp3 = new PositionParameter(  pos5, prmt16, 25 );
        PositionParameter pp4 = new PositionParameter(  pos5, prmt21, 25 );

        PositionParameter pp5 = new PositionParameter(  pos11, prmt17, 20 );
        PositionParameter pp6 = new PositionParameter(  pos11, prmt19, 20 );
        PositionParameter pp7 = new PositionParameter(  pos11, prmt20, 30 );
        PositionParameter pp8 = new PositionParameter(  pos11, prmt21, 30 );

        PositionParameter pp9 = new PositionParameter(  pos14, prmt15, 10 );
        PositionParameter pp10 = new PositionParameter( pos14, prmt21, 20 );
        PositionParameter pp11 = new PositionParameter( pos14, prmt25, 30 );
        PositionParameter pp12 = new PositionParameter( pos14, prmt19, 20 );
        PositionParameter pp13 = new PositionParameter( pos14, prmt20, 20 );

        

        positionParameterRepository.saveAll ( Arrays.asList(pp1, pp2, pp3, pp4, pp5, pp6, pp7, pp8, pp9, pp10, pp11, pp12, pp13) );

        /* ESTATÍSTICA DOS ATACANTES */
        // PlayerParameter pylp2 = new PlayerParameter( player28, prmt21, 30 );
        // PlayerParameter pylp3 = new PlayerParameter( player28, prmt25, 30 );
        // PlayerParameter pylp1 = new PlayerParameter( player28, prmt15, 30 );

        // PlayerParameter pylp4 = new PlayerParameter( player29, prmt15, 90 );
        // PlayerParameter pylp5 = new PlayerParameter( player29, prmt21, 90 );
        // PlayerParameter pylp6 = new PlayerParameter( player29, prmt25, 90 );

        // PlayerParameter pylp7 = new PlayerParameter( player30, prmt15, 50 );
        // PlayerParameter pylp8 = new PlayerParameter( player30, prmt21, 50 );
        // PlayerParameter pylp9 = new PlayerParameter( player30, prmt25, 50 );

        // PlayerParameter pylp10 = new PlayerParameter( player31, prmt15, 100 );
        // PlayerParameter pylp11 = new PlayerParameter( player31, prmt21, 100 );
        // PlayerParameter pylp12 = new PlayerParameter( player31, prmt25, 100 );

        // PlayerParameter pylp13 = new PlayerParameter( player32, prmt15, 70 );
        // PlayerParameter pylp14 = new PlayerParameter( player32, prmt21, 70 );
        // PlayerParameter pylp15 = new PlayerParameter( player32, prmt25, 70 );
        
        // PlayerParameter pylp16 = new PlayerParameter( player33, prmt15, 20 );
        // PlayerParameter pylp17 = new PlayerParameter( player33, prmt21, 20 );
        // PlayerParameter pylp18 = new PlayerParameter( player33, prmt25, 20 );
        
        // PlayerParameter pylp19 = new PlayerParameter( player34, prmt15, 60 );
        // PlayerParameter pylp20 = new PlayerParameter( player34, prmt21, 60 );
        // PlayerParameter pylp21 = new PlayerParameter( player34, prmt25, 60 );
        
        // PlayerParameter pylp22 = new PlayerParameter( player35, prmt15, 40 );
        // PlayerParameter pylp23 = new PlayerParameter( player35, prmt21, 40 );
        // PlayerParameter pylp24 = new PlayerParameter( player35, prmt25, 40 );
        
        // PlayerParameter pylp25 = new PlayerParameter( player36, prmt15, 10 );
        // PlayerParameter pylp26 = new PlayerParameter( player36, prmt21, 10 );
        // PlayerParameter pylp27 = new PlayerParameter( player36, prmt25, 10 );
        
        // PlayerParameter pylp28 = new PlayerParameter( player37, prmt15, 80 );
        // PlayerParameter pylp29 = new PlayerParameter( player37, prmt21, 80 );
        // PlayerParameter pylp30 = new PlayerParameter( player37, prmt25, 80 );
        
        // PlayerParameter pylp31 = new PlayerParameter( player38, prmt15, 85 );
        // PlayerParameter pylp32 = new PlayerParameter( player38, prmt21, 85 );
        // PlayerParameter pylp33 = new PlayerParameter( player38, prmt25, 85 );

        PlayerParameter pylp1 = new PlayerParameter( player28, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp2 = new PlayerParameter( player28, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp3 = new PlayerParameter( player28, prmt25, random.nextInt(30, 100) );

        PlayerParameter pylp4 = new PlayerParameter( player29, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp5 = new PlayerParameter( player29, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp6 = new PlayerParameter( player29, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp7 = new PlayerParameter( player30, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp8 = new PlayerParameter( player30, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp9 = new PlayerParameter( player30, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp10 = new PlayerParameter( player31, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp11 = new PlayerParameter( player31, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp12 = new PlayerParameter( player31, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp13 = new PlayerParameter( player32, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp14 = new PlayerParameter( player32, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp15 = new PlayerParameter( player32, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp16 = new PlayerParameter( player33, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp17 = new PlayerParameter( player33, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp18 = new PlayerParameter( player33, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp19 = new PlayerParameter( player34, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp20 = new PlayerParameter( player34, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp21 = new PlayerParameter( player34, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp22 = new PlayerParameter( player35, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp23 = new PlayerParameter( player35, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp24 = new PlayerParameter( player35, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp25 = new PlayerParameter( player36, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp26 = new PlayerParameter( player36, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp27 = new PlayerParameter( player36, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp28 = new PlayerParameter( player37, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp29 = new PlayerParameter( player37, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp30 = new PlayerParameter( player37, prmt25, random.nextInt(30, 100) );
        
        PlayerParameter pylp31 = new PlayerParameter( player38, prmt15, random.nextInt(30, 100) );
        PlayerParameter pylp32 = new PlayerParameter( player38, prmt21, random.nextInt(30, 100) );
        PlayerParameter pylp33 = new PlayerParameter( player38, prmt25, random.nextInt(30, 100) );

        /*-------------------------------------------------------------------------------------*/

        PlayerParameter pylp34 = new PlayerParameter( player28, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp35 = new PlayerParameter( player28, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp36 = new PlayerParameter( player29, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp37 = new PlayerParameter( player29, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp38 = new PlayerParameter( player30, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp39 = new PlayerParameter( player30, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp40 = new PlayerParameter( player31, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp41 = new PlayerParameter( player31, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp42 = new PlayerParameter( player32, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp43 = new PlayerParameter( player32, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp44 = new PlayerParameter( player33, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp45 = new PlayerParameter( player33, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp46 = new PlayerParameter( player34, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp47 = new PlayerParameter( player34, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp48 = new PlayerParameter( player35, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp49 = new PlayerParameter( player35, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp50 = new PlayerParameter( player36, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp51 = new PlayerParameter( player36, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp52 = new PlayerParameter( player37, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp53 = new PlayerParameter( player37, prmt20, random.nextInt(30, 100) );

        PlayerParameter pylp54 = new PlayerParameter( player38, prmt19, random.nextInt(30, 100) );
        PlayerParameter pylp55 = new PlayerParameter( player38, prmt20, random.nextInt(30, 100) );

        playerParameterRepository.saveAll (
            Arrays.asList (
                pylp1, pylp2, pylp3, pylp4, pylp5, pylp6, pylp7, pylp8, pylp9, pylp10, pylp11, pylp12, pylp13, pylp14, pylp15, pylp16, pylp17, pylp18, pylp19, pylp20, pylp21, pylp22, pylp23, pylp24, pylp25, pylp26, pylp27, pylp28, pylp29, pylp30, pylp31, pylp32, pylp33, pylp34, pylp35,  pylp36, pylp37, pylp38, pylp39, pylp40,
                pylp41, pylp42, pylp43, pylp44, pylp45, pylp46, pylp47, pylp48, pylp49, pylp50, pylp51, pylp52, pylp53, pylp54, pylp55
            )
        );
    
    }
    
}
