package gg.vh.modsuite.file;

import lombok.NonNull;

import java.io.File;

public interface DataFileFactory {
    DataFile newDataFile(@NonNull File file);
}
