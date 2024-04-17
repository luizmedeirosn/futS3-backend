package com.luizmedeirosn.futs3.configs;

import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Configuration
public class StartupSeedConfig implements CommandLineRunner {

    private final PlayerRepository playerRepository;

    public StartupSeedConfig(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        final String PICTURES_FOLDER_PATH = "./src/main/java/com/luizmedeirosn/futs3/configs/data/sport-pictures";
        File picturesFolder = new File(PICTURES_FOLDER_PATH);
        File[] picturesArray = picturesFolder.listFiles(File::isFile);

        List<File> picturesList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(picturesArray)));
        picturesList.sort(Comparator.comparingInt(f -> Integer.parseInt(f.getName().split("-")[0])));

        for (int i = 1; i <= 38; i++) {
            Player player = playerRepository.findById((long)i).orElseThrow();
            File pictureFile = picturesList.get(i-1);
            byte[] pictureData = Files.readAllBytes(Paths.get(pictureFile.getAbsolutePath()));

            PlayerPicture picture = new PlayerPicture(
                    pictureFile.getName(), "image/webp", pictureData, player);

            if (player.getPlayerPicture() == null) {
                player.setPlayerPicture(picture);
                playerRepository.save(player);
            }
        }
    }
}
