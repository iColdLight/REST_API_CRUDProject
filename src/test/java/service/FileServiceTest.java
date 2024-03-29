package service;

import com.coldlight.restapicrudapp.entity.FileEntity;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateFileRepositoryImpl;
import com.coldlight.restapicrudapp.service.FileService;
import com.coldlight.restapicrudapp.util.HibernateUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {Hibernate.class, HibernateUtils.class})
public class FileServiceTest {

    @Mock
    private HibernateFileRepositoryImpl hibernateFileRepository;

    @InjectMocks
    private FileService fileService;

    @Captor
    private ArgumentCaptor<FileEntity> fileEntityArgumentCaptor;

    private LobCreator mockLobCreator;

    @Before
    public void init(){
        Session mockSession = PowerMockito.mock(Session.class);
        mockLobCreator = PowerMockito.mock(LobCreator.class);
        PowerMockito.mockStatic(HibernateUtils.class);
        PowerMockito.when(HibernateUtils.getSession()).thenReturn(mockSession);
        PowerMockito.mockStatic(Hibernate.class);
        PowerMockito.when(Hibernate.getLobCreator(mockSession)).thenReturn(mockLobCreator);
    }

   @Test
    public void createFileTest() {
        //given
        String fileName = "Family Image";
        FileEntity file = new FileEntity();
        file.setName(fileName);


        //when
        when(hibernateFileRepository.save(file)).thenReturn(file);

        //then
        assertEquals(file, fileService.createFile(fileName, "filePath", null));
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
        fileService.createFile(fileName, "filePath", null);
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
        fileService.deleteFileByID(1L);
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
        fileService.deleteFileByID(1L);
        Assert.assertEquals(1L, fileEntityArgumentCaptor.getValue().getId());
    }
}

