package common.repository;

import java.io.InputStream;

public interface IAudioRepository {

    InputStream getStream(String fileName);
}
