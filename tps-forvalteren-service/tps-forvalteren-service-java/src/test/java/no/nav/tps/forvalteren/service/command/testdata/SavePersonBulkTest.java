package no.nav.tps.forvalteren.service.command.testdata;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import no.nav.tps.forvalteren.domain.jpa.Person;
import no.nav.tps.forvalteren.repository.jpa.PersonRepository;

@RunWith(MockitoJUnitRunner.class)
public class SavePersonBulkTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private SavePersonBulk savePersonBulk;

    @Mock
    private List<Person> persons;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private DataIntegrityViolationException dataIntegrityViolationException;

    @Mock
    private Throwable throwable;
    
    @Test
    public void checkThatPersonGetsSaved() {
        when(persons.size()).thenReturn(1);

        savePersonBulk.execute(persons);

        verify(personRepository).save(persons);
    }

    @Test
    public void checkThatAllPersonsGetSaved() {
        when(persons.size()).thenReturn(10000);

        savePersonBulk.execute(persons);

        verify(personRepository, times(10)).save(anyListOf(Person.class));
    }

    @Test
    public void checkThatExceptionIsThrown() {
        when(persons.size()).thenReturn(1);
        when(personRepository.save(persons)).thenThrow(dataIntegrityViolationException);
        when(dataIntegrityViolationException.getCause()).thenReturn(throwable);

        expectedException.expect(DataIntegrityViolationException.class);
        expectedException.expectMessage(
                "En T_PERSON DB constraint er brutt! Kan ikke lagre Person. "
                        + "Error: null Cause: null; nested exception is dataIntegrityViolationException");

        savePersonBulk.execute(persons);
    }
}