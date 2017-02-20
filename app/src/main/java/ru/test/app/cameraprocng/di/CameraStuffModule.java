package ru.test.app.cameraprocng.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.test.app.cameraprocng.SurfaceWorker;

/**
 * Created by vyacheslav.rogov on 20.02.2017.
 */

@Module
public class CameraStuffModule {
    @Provides
    @Singleton
    SurfaceWorker provideSurfaceWorker() {
        return new SurfaceWorker();
    }
}
