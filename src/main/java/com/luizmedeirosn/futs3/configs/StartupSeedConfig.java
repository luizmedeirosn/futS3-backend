package com.luizmedeirosn.futs3.configs;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupSeedConfig implements CommandLineRunner {

  private final PlayerRepository playerRepository;

  public StartupSeedConfig(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    var picturesArray =
        new File("./src/main/java/com/luizmedeirosn/futs3/configs/data/sport-pictures").listFiles(File::isFile);

    var picturesList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(picturesArray)));
    picturesList.sort(Comparator.comparingInt(f -> Integer.parseInt(f.getName().split("-")[0])));

    for (int i = 1; i <= 38; i++) {
      var playerOptional = playerRepository.findById((long) i);
      if (playerOptional.isPresent()) {
        var player = playerOptional.get();
        var pictureFile = picturesList.get(i - 1);
        var picture =
            new PlayerPicture(
                pictureFile.getName(),
                "image/webp",
                Files.readAllBytes(Paths.get(pictureFile.getAbsolutePath())),
                player);

        if (player.getPlayerPicture() == null) {
          player.setPlayerPicture(picture);
          playerRepository.save(player);
        }
      }
    }
  }
}
