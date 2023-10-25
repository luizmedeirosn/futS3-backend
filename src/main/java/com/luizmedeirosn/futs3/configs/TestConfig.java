package com.luizmedeirosn.futs3.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerParameter;
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
        
        Position pos1 = new Position( "GOLEIRO",            "GOLEIRO: defensor do gol" );
        Position pos2 = new Position( "LATERAL DIREITO",    "LATERAL DIREITO: responsável por avançar e apoiar a defesa" );
        Position pos3 = new Position( "ZAGUEIRO DESTRO",    "ZAGUEIRO DESTRO: responsável pela proteção do gol" );
        Position pos4 = new Position( "ZAGUEIRO CANHOTO",   "ZAGUEIRO CANHOTO: responsável pela proteção do gol" );
        Position pos5 = new Position( "LATERAL ESQUERDO",   "LATERAL ESQUERDO: responsável por avançar e apoiar a defesa" );
        Position pos6 = new Position( "TERCEIRO ZAGUEIRO",  "TERCEIRO ZAGUEIRO: zagueiro que atua como um líbero, auxiliando a defesa" );
        Position pos7 = new Position( "PRIMEIRO VOLANTE",   "PRIMEIRO VOLANTE: responsável pela marcação e pela transição da defesa para o ataque" );
        Position pos8 = new Position( "SEGUNDO VOLANTE",    "SEGUNDO VOLANTE: responsável por auxiliar o primeiro volante, equilibrando o meio de campo" );
        Position pos9 = new Position( "MEIA DIREITO",       "MEIA DIREITO: responsável pela criação de jogadas no meio de campo" );
        Position pos10 = new Position( "MEIA ESQUERDO",     "MEIA ESQUERDO: responsável pela criação de jogadas no meio de campo" );
        Position pos11 = new Position( "MEIA-ATACANTE",     "MEIA-ATACANTE: jogador que atua tanto na criação quanto na finalização das jogadas" );
        Position pos12 = new Position( "PONTA DIREIRA",     "PONTA DIREITA: responsável por avançar pelas laterais e cruzar bolas na área" );
        Position pos13 = new Position( "PONTA ESQUERDA",    "PONTA ESQUERDA: responsável por avançar pelas laterais e cruzar bolas na área" );
        Position pos14 = new Position( "ATACANTE",          "ATACANTE: responsável por concluir as jogadas, marcando gols" );
        Position pos15 = new Position( "CENTROAVANTE",      "CENTROAVANTE: jogador que joga mais próximo à área adversária, responsável por concluir as jogadas com gols" );

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
        Player player1 = new Player( "Denis",   pos1, null);
        Player player2 = new Player( "Denival", pos1, null );
        Player player3 = new Player( "Renan",   pos1, null );
        Player player4 = new Player( "Jordan",  pos1, null );
        Player player5 = new Player( "Adriano", pos1, null );

        /* LATERAL ESQUERDO */ 
        Player player6 = new Player( "Roberto Rosales", pos5, null );
        Player player7 = new Player( "Everton",         pos5, null );
        Player player8 = new Player( "Felipinho",       pos5, null );
        Player player9 = new Player( "Igor Cariús",     pos5, null );
        Player player10 = new Player( "Nathan",         pos5, null );
        Player player11 = new Player( "Eduardo",        pos5, null );
        Player player12 = new Player( "Victor Gabriel", pos5, null );

        // ZAGUEIRO DESTRO
        Player player13 = new Player( "Alisson Cassiano",   pos3, null);
        Player player14 = new Player( "Renzo",              pos3, null );
        Player player15 = new Player( "Rafael Thyere",      pos3, null );
        Player player16 = new Player( "Sabino",             pos3, null );
        Player player17 = new Player( "Chico",              pos3, null );
        Player player18 = new Player( "Matheus Baraka",     pos3, null );


        /* PRIMEIRO VOLANTE */
        Player player19 = new Player("Felipe",          pos7, null );
        Player player20 = new Player("Ronaldo",         pos7, null );
        Player player21 = new Player("Fabinho",         pos7, null );
        Player player22 = new Player("Pedro Martins",   pos7, null );
        Player player23 = new Player("Fábio Matheus",   pos7, null );
        Player player24 = new Player("Lucas André",     pos7, null );

        /* MEIA DIREITO */
        Player player25 = new Player("Alan Ruiz",       pos9, null );
        Player player26 = new Player("Jorginho",        pos9, null );
        Player player27 = new Player("Luciano Juba",    pos9, null );
        Player player28 = new Player("Juan Xavier",     pos9, null );

        /* ATACANTES */
        Player player29 = new Player("Michael Lima",       pos14, null );
        Player player30 = new Player("Peglow",             pos14, null );
        Player player31 = new Player("Diego Souza",        pos14, null );
        Player player32 = new Player("Vagner Love",        pos14, null );
        Player player33 = new Player("Edinho",             pos14, null );
        Player player34 = new Player("Facundo Labandeira", pos14, null );
        Player player35 = new Player("Gean Carlos",        pos14, null );
        Player player36 = new Player("Wanderson",          pos14, null );
        Player player37 = new Player("Gabriel Santos",     pos14, null );
        Player player38 = new Player("Fabrício Daniel",    pos14, null );
        
        List<Player> players = new ArrayList<>(
            Arrays.asList (
                player1, player2, player3 ,player4, player5, player6, player7, player8, player9, player10, player11, player12, player13, player14, player15, player16, player17, player18, player19, player20, player21, player21, player22, player23, player24, player25, player26, player27, player28, player29, player30, player31, player32, player33, player34, player35, player36, player37, player38
            )
        );
        playerRepository.saveAll(players);

        PositionParameter pp1 = new PositionParameter(  pos5, prmt14, 25 );
        PositionParameter pp2 = new PositionParameter(  pos5, prmt15, 25 );
        PositionParameter pp3 = new PositionParameter(  pos5, prmt16, 25 );
        PositionParameter pp4 = new PositionParameter(  pos5, prmt21, 25 );

        PositionParameter pp5 = new PositionParameter(  pos11, prmt17, 20 );
        PositionParameter pp6 = new PositionParameter(  pos11, prmt19, 20 );
        PositionParameter pp7 = new PositionParameter(  pos11, prmt20, 30 );
        PositionParameter pp8 = new PositionParameter(  pos11, prmt21, 30 );

        PositionParameter pp9 = new PositionParameter(  pos14, prmt15, 10 );
        PositionParameter pp10 = new PositionParameter(  pos14, prmt21, 40 );
        PositionParameter pp11 = new PositionParameter(  pos14, prmt25, 50 );

        

        positionParameterRepository.saveAll( Arrays.asList(pp1, pp2, pp3, pp4, pp5, pp6, pp7, pp8, pp9, pp10, pp11) );

        /* ESTATÍSTICA DOS ATACANTES */
        PlayerParameter pylp1 = new PlayerParameter( player29, prmt15, 30 );
        PlayerParameter pylp2 = new PlayerParameter( player29, prmt21, 30 );
        PlayerParameter pylp3 = new PlayerParameter( player29, prmt25, 30 );

        PlayerParameter pylp4 = new PlayerParameter( player30, prmt15, 90 );
        PlayerParameter pylp5 = new PlayerParameter( player30, prmt21, 90 );
        PlayerParameter pylp6 = new PlayerParameter( player30, prmt25, 90 );

        PlayerParameter pylp7 = new PlayerParameter( player31, prmt15, 50 );
        PlayerParameter pylp8 = new PlayerParameter( player31, prmt21, 50 );
        PlayerParameter pylp9 = new PlayerParameter( player31, prmt25, 50 );

        PlayerParameter pylp10 = new PlayerParameter( player32, prmt15, 100 );
        PlayerParameter pylp11 = new PlayerParameter( player32, prmt21, 100 );
        PlayerParameter pylp12 = new PlayerParameter( player32, prmt25, 100 );

        PlayerParameter pylp13 = new PlayerParameter( player33, prmt15, 70 );
        PlayerParameter pylp14 = new PlayerParameter( player33, prmt21, 70 );
        PlayerParameter pylp15 = new PlayerParameter( player33, prmt25, 70);

        PlayerParameter pylp16 = new PlayerParameter( player34, prmt15, 20 );
        PlayerParameter pylp17 = new PlayerParameter( player34, prmt21, 20 );
        PlayerParameter pylp18 = new PlayerParameter( player34, prmt25, 20 );

        PlayerParameter pylp19 = new PlayerParameter( player35, prmt15, 60 );
        PlayerParameter pylp20 = new PlayerParameter( player35, prmt21, 60 );
        PlayerParameter pylp21 = new PlayerParameter( player35, prmt25, 60 );

        PlayerParameter pylp22 = new PlayerParameter( player36, prmt15, 40 );
        PlayerParameter pylp23 = new PlayerParameter( player36, prmt21, 40 );
        PlayerParameter pylp24 = new PlayerParameter( player36, prmt25, 40 );

        PlayerParameter pylp25 = new PlayerParameter( player37, prmt15, 10 );
        PlayerParameter pylp26 = new PlayerParameter( player37, prmt21, 10 );
        PlayerParameter pylp27 = new PlayerParameter( player37, prmt25, 10 );

        PlayerParameter pylp28 = new PlayerParameter( player38, prmt15, 80 );
        PlayerParameter pylp29 = new PlayerParameter( player38, prmt21, 80 );
        PlayerParameter pylp30 = new PlayerParameter( player38, prmt25, 80 );

        // java.util.Random random = new java.util.Random();
        // PlayerParameter pylp1 = new PlayerParameter( player23, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp2 = new PlayerParameter( player23, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp3 = new PlayerParameter( player23, prmt25, random.nextInt(1, 100) );

        // PlayerParameter pylp4 = new PlayerParameter( player24, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp5 = new PlayerParameter( player24, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp6 = new PlayerParameter( player24, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp7 = new PlayerParameter( player25, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp8 = new PlayerParameter( player25, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp9 = new PlayerParameter( player25, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp10 = new PlayerParameter( player26, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp11 = new PlayerParameter( player26, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp12 = new PlayerParameter( player26, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp13 = new PlayerParameter( player27, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp14 = new PlayerParameter( player27, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp15 = new PlayerParameter( player27, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp16 = new PlayerParameter( player28, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp17 = new PlayerParameter( player28, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp18 = new PlayerParameter( player28, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp19 = new PlayerParameter( player29, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp20 = new PlayerParameter( player29, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp21 = new PlayerParameter( player29, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp22 = new PlayerParameter( player30, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp23 = new PlayerParameter( player30, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp24 = new PlayerParameter( player30, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp25 = new PlayerParameter( player31, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp26 = new PlayerParameter( player31, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp27 = new PlayerParameter( player31, prmt25, random.nextInt(1, 100) );
        
        // PlayerParameter pylp28 = new PlayerParameter( player32, prmt15, random.nextInt(1, 100) );
        // PlayerParameter pylp29 = new PlayerParameter( player32, prmt21, random.nextInt(1, 100) );
        // PlayerParameter pylp30 = new PlayerParameter( player32, prmt25, random.nextInt(1, 100) );

        playerParameterRepository.saveAll (
            Arrays.asList (
                pylp1, pylp2, pylp3, pylp4, pylp5, pylp6, pylp7, pylp8, pylp9, pylp10, pylp11, pylp12, pylp13, pylp14, pylp15, pylp16, pylp17, pylp18, pylp19, pylp20, pylp21, pylp22, pylp23, pylp24, pylp25, pylp26, pylp27, pylp28, pylp29, pylp30
            )
        );


    
    }
    
}
