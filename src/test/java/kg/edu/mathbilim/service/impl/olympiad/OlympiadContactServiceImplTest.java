package kg.edu.mathbilim.service.impl.olympiad;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import kg.edu.mathbilim.model.ContactType;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.model.olympiad.OlympiadContactId;
import kg.edu.mathbilim.repository.ContactTypeRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OlympiadContactServiceImplTest {
    @Mock
    private OlympiadContactRepository repository;
    @Mock
    private ContactTypeRepository contactTypeRepository;
    @InjectMocks
    private OlympiadContactServiceImpl service;

        @InjectMocks
        private OlympiadContactServiceImpl olympiadContactService;

        private OlympiadContact contact1;
        private OlympiadContact contact2;
        private List<OlympiadContact> contactList;
        private Olympiad olympiad;
        private ContactType contactType;

        @BeforeEach
        void setUp() {
            setupTestData();
        }

        private void setupTestData() {
            olympiad = Olympiad.builder()
                    .id(1L)
                    .title("Test Olympiad")
                    .build();

            contactType = ContactType.builder()
                    .id(1L)
                    .name("Email")
                    .build();

            OlympiadContactId contactId1 = new OlympiadContactId();
            contactId1.setOlympiadId(1L);
            contactId1.setContactTypeId(1L);

            OlympiadContactId contactId2 = new OlympiadContactId();
            contactId2.setOlympiadId(1L);
            contactId2.setContactTypeId(2L);

            contact1 = OlympiadContact.builder()
                    .id(contactId1)
                    .olympiad(olympiad)
                    .contactType(contactType)
                    .info("test1@example.com")
                    .build();

            contact2 = OlympiadContact.builder()
                    .id(contactId2)
                    .olympiad(olympiad)
                    .contactType(ContactType.builder().id(2L).name("Phone").build())
                    .info("+1234567890")
                    .build();

            contactList = Arrays.asList(contact1, contact2);
        }

        @Test
        void getContactsByOlympId_WithExistingContacts_ReturnsContactList() {
            int olympId = 1;
            when(repository.getByOlympiad_Id(olympId)).thenReturn(contactList);

            List<OlympiadContact> result = olympiadContactService.getContactsByOlympId(olympId);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("test1@example.com", result.get(0).getInfo());
            assertEquals("+1234567890", result.get(1).getInfo());

            verify(repository).getByOlympiad_Id(olympId);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(contactTypeRepository);
        }

        @Test
        void getContactsByOlympId_WithNonExistingOlympiad_ReturnsEmptyList() {
            int olympId = 999;
            when(repository.getByOlympiad_Id(olympId)).thenReturn(Collections.emptyList());

            List<OlympiadContact> result = olympiadContactService.getContactsByOlympId(olympId);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(repository).getByOlympiad_Id(olympId);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(contactTypeRepository);
        }

        @Test
        void getContactsByOlympId_WithZeroId_CallsRepository() {
            int olympId = 0;
            when(repository.getByOlympiad_Id(olympId)).thenReturn(Collections.emptyList());

            List<OlympiadContact> result = olympiadContactService.getContactsByOlympId(olympId);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(repository).getByOlympiad_Id(olympId);
        }

        @Test
        void getContactsByOlympId_WithNegativeId_CallsRepository() {
            int olympId = -1;
            when(repository.getByOlympiad_Id(olympId)).thenReturn(Collections.emptyList());

            List<OlympiadContact> result = olympiadContactService.getContactsByOlympId(olympId);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(repository).getByOlympiad_Id(olympId);
        }

        @Test
        void addAllContacts_WithValidContactList_SavesAllContacts() {
            when(repository.saveAll(contactList)).thenReturn(contactList);

            olympiadContactService.addAllContacts(contactList);

            verify(repository).saveAll(contactList);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(contactTypeRepository);
        }

        @Test
        void addAllContacts_WithEmptyList_CallsSaveAll() {
            List<OlympiadContact> emptyList = Collections.emptyList();
            when(repository.saveAll(emptyList)).thenReturn(emptyList);

            olympiadContactService.addAllContacts(emptyList);

            verify(repository).saveAll(emptyList);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(contactTypeRepository);
        }

        @Test
        void addAllContacts_WithSingleContact_SavesContact() {
            List<OlympiadContact> singleContactList = Arrays.asList(contact1);
            when(repository.saveAll(singleContactList)).thenReturn(singleContactList);

            olympiadContactService.addAllContacts(singleContactList);

            verify(repository).saveAll(singleContactList);
            verify(repository).saveAll(argThat(arg -> {
                List<OlympiadContact> contacts = (List<OlympiadContact>) arg;
                return contacts.size() == 1 &&
                        "test1@example.com".equals(contacts.getFirst().getInfo());
            }));

        }

        @Test
        void addAllContacts_WithNullList_CallsSaveAll() {
            List<OlympiadContact> nullList = null;

            assertDoesNotThrow(() -> {
                olympiadContactService.addAllContacts(nullList);
            });

            verify(repository).saveAll(nullList);
        }

        @Test
        void addAllContacts_WithLargeContactList_SavesAllContacts() {
            List<OlympiadContact> largeList = Arrays.asList(
                    contact1, contact2, contact1, contact2, contact1
            );
            when(repository.saveAll(largeList)).thenReturn(largeList);

            olympiadContactService.addAllContacts(largeList);

            verify(repository).saveAll(largeList);
            verify(repository).saveAll(argThat(arg -> {
                if (!(arg instanceof List)) return false;
                List<?> contacts = (List<?>) arg;
                return contacts.size() == 5;
            }));        }

        @Test
        void deleteByOlympiadId_WithValidId_CallsRepository() {
            Long olympId = 1L;

            olympiadContactService.deleteByOlympiadId(olympId);

            verify(repository).deleteByOlympiadId(olympId);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(contactTypeRepository);
        }

        @Test
        void deleteByOlympiadId_WithNonExistentId_CallsRepository() {
            Long olympId = 999L;

            olympiadContactService.deleteByOlympiadId(olympId);

            verify(repository).deleteByOlympiadId(olympId);
            verifyNoMoreInteractions(repository);
        }

        @Test
        void deleteByOlympiadId_WithNullId_CallsRepository() {
            Long olympId = null;

            olympiadContactService.deleteByOlympiadId(olympId);

            verify(repository).deleteByOlympiadId(olympId);
            verifyNoMoreInteractions(repository);
        }

        @Test
        void deleteByOlympiadId_WithZeroId_CallsRepository() {
            Long olympId = 0L;
            olympiadContactService.deleteByOlympiadId(olympId);
            verify(repository).deleteByOlympiadId(olympId);
        }

        @Test
        void deleteByOlympiadId_WithNegativeId_CallsRepository() {
            Long olympId = -1L;
            olympiadContactService.deleteByOlympiadId(olympId);
            verify(repository).deleteByOlympiadId(olympId);
        }

        @Test
        void serviceIntegration_CreateAndDeleteFlow() {
            Long olympId = 1L;
            when(repository.saveAll(contactList)).thenReturn(contactList);
            olympiadContactService.addAllContacts(contactList);
            olympiadContactService.deleteByOlympiadId(olympId);

            verify(repository).saveAll(contactList);
            verify(repository).deleteByOlympiadId(olympId);
            verifyNoMoreInteractions(repository);
        }

        @Test
        void serviceIntegration_GetContactsAfterAdding() {
            int olympId = 1;
            when(repository.saveAll(contactList)).thenReturn(contactList);
            when(repository.getByOlympiad_Id(olympId)).thenReturn(contactList);

            olympiadContactService.addAllContacts(contactList);
            List<OlympiadContact> result = olympiadContactService.getContactsByOlympId(olympId);

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(repository).saveAll(contactList);
            verify(repository).getByOlympiad_Id(olympId);
        }

        @Test
        void allMethods_DoNotInteractWithContactTypeRepository() {
            int olympId = 1;
            Long olympIdLong = 1L;
            when(repository.getByOlympiad_Id(olympId)).thenReturn(contactList);
            when(repository.saveAll(contactList)).thenReturn(contactList);

            olympiadContactService.getContactsByOlympId(olympId);
            olympiadContactService.addAllContacts(contactList);
            olympiadContactService.deleteByOlympiadId(olympIdLong);

            verifyNoInteractions(contactTypeRepository);
        }

}
