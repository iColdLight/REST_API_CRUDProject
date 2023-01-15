package service;

import com.coldlight.restapicrudapp.entity.FileEntity;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateFileRepositoryImpl;
import com.coldlight.restapicrudapp.service.FileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    @Mock
    private HibernateFileRepositoryImpl hibernateFileRepository;

    @InjectMocks
    private FileService fileService;

    @Captor
    ArgumentCaptor<FileEntity> fileEntityArgumentCaptor;

    @Test
    public void createFileTest() {
        //given
        String fileName = "Family Image";
        byte[] payload = new byte[124];
        FileEntity file = new FileEntity();
        file.setName(fileName);


        //when
        when(hibernateFileRepository.save(file)).thenReturn(file);

        //then
        assertEquals(file, fileService.createFile(fileName, "filePath", null, null, payload));

    }

    @Test(expected = RuntimeException.class)
    public void createFileExceptionTest() {
        //given
        String fileName = "Family Image";
        FileEntity file = new FileEntity();
        file.setName(fileName);

        //when
        when(hibernateFileRepository.save(file)).thenThrow(new RuntimeException());

        //then
        fileService.createFile(fileName, "filePath", null, null, null);
    }

    @Test
    public void getAllFilesEmptyTest() {
        //when
        when(hibernateFileRepository.getAll()).thenReturn(new ArrayList<>());

        //then
        assertTrue(fileService.getAllFiles().isEmpty());
    }

    @Test
    public void getAllFilesTest() {
        //given
        String name = "fileName";
        String filePath = "filePath";
        FileEntity file = FileEntity.builder()
                .name(name)
                .filePath(filePath)
                .build();

        String name2 = "fileName";
        String filePath2 = "filePath";
        FileEntity file2 = FileEntity.builder()
                .name(name2)
                .filePath(filePath2)
                .build();

        //when
        when(hibernateFileRepository.getAll()).thenReturn(List.of(file, file2));

        //then
        List<FileEntity> allFiles = fileService.getAllFiles();
        Assert.assertEquals(2, allFiles.size());

        FileEntity firstFile = allFiles.get(0);
        Assert.assertEquals(name, firstFile.getName());
        Assert.assertEquals(filePath, firstFile.getFilePath());

        FileEntity secondFile = allFiles.get(1);
        Assert.assertEquals(name2, secondFile.getName());
        Assert.assertEquals(filePath2, secondFile.getFilePath());
    }

    @Test(expected = RuntimeException.class)
    public void getFilesExceptionTest() {


        //when
        when(hibernateFileRepository.getAll()).thenThrow(new RuntimeException());

        //then
        fileService.getAllFiles();

    }

    @Test
    public void getFileByIDTest() {
        //given
        String name = "fileName";
        String filePath = "filePath";
        FileEntity file = FileEntity.builder()
                .name(name)
                .filePath(filePath)
                .build();
        Long id = 1L;

        //when
        when(hibernateFileRepository.getByID(id)).thenReturn(file);

        //then
        FileEntity fileByID = fileService.getFileByID(id);
        Assert.assertEquals(name, fileByID.getName());
        Assert.assertEquals(filePath, fileByID.getFilePath());

    }

    @Test(expected = RuntimeException.class)
    public void getFileByIDException() {
        //given
        Long id = 1L;

        //when
        when(hibernateFileRepository.getByID(id)).thenThrow(new RuntimeException());

        //then
        fileService.getFileByID(id);
    }

    @Test(expected = RuntimeException.class)
    public void deleteFileNotFoundTest() {
        //when
        when(hibernateFileRepository.getByID(any())).thenReturn(null);

        //then
        fileService.deleteFileByID(1L, null);
    }

    @Test
    public void deleteFileTest() {
        String name = "fileName";
        String filePath = "filePath";
        FileEntity file = FileEntity.builder()
                .id(1L)
                .name(name)
                .filePath(filePath)
                .build();

        //when
        when(hibernateFileRepository.getByID(1L)).thenReturn(file);
        doNothing().when(hibernateFileRepository).delete(fileEntityArgumentCaptor.capture());


        //then
        fileService.deleteFileByID(1L, null);
        Assert.assertEquals(1L, fileEntityArgumentCaptor.getValue().getId());
    }
}

