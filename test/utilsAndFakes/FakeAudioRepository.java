package utilsAndFakes;

import serverSide.repositories.IAudioRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FakeAudioRepository implements IAudioRepository {
    @Override
    public InputStream getStream(String fileName) {
        return new ByteArrayInputStream(new byte[]{});
    }
}
