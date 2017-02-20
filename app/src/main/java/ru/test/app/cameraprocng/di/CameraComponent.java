package ru.test.app.cameraprocng.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.test.app.cameraprocng.MainActivity;
import ru.test.app.cameraprocng.SurfaceWorker;

/**
 * Created by vyacheslav.rogov on 20.02.2017.
 */

@Singleton
@Component(modules = CameraStuffModule.class)
public interface CameraComponent {
    SurfaceWorker provideSurfaceWorker();
}
