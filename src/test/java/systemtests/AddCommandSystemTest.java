//package systemtests;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.COORDINATE_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.COORDINATE_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_COORDINATE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARPARK_NO_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARPARK_TYPE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_NO_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_NO_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_TYPE_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.CARPARK_TYPE_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_2;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_2;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.testutil.TypicalCarparks.ALICE;
//import static seedu.address.testutil.TypicalCarparks.AMY;
//import static seedu.address.testutil.TypicalCarparks.BOB;
//import static seedu.address.testutil.TypicalCarparks.CARL;
//import static seedu.address.testutil.TypicalCarparks.HOON;
//import static seedu.address.testutil.TypicalCarparks.IDA;
//import static seedu.address.testutil.TypicalCarparks.KEYWORD_MATCHING_MEIER;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.Model;
//import seedu.address.model.carpark.Address;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.CarparkBuilder;
//import seedu.address.testutil.PersonUtil;
//
//public class AddCommandSystemTest extends AddressBookSystemTest {
//
//    @Test
//    public void add() {
//        Model model = getModel();
//
//        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */
//
//        /* Case: add a carpark without tags to a non-empty address book, command with leading spaces and trailing spaces
//         * -> added
//         */
//        Person toAdd = AMY;
//        String command = "   " + AddCommand.COMMAND_WORD + "  " + CARPARK_NO_DESC_1 + "  " + CARPARK_TYPE_DESC_1 + " "
//                + COORDINATE_DESC_1 + "   " + ADDRESS_DESC_1 + "   " + TAG_DESC_FRIEND + " ";
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: undo adding Amy to the list -> Amy deleted */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: redo adding Amy to the list -> Amy added again */
//        command = RedoCommand.COMMAND_WORD;
//        model.addPerson(toAdd);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: add a carpark with all fields same as another carpark in the address book except name -> added */
//        toAdd = new CarparkBuilder(AMY).withName(VALID_CARPARK_NUMBER_2).build();
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_2 + CARPARK_TYPE_DESC_1 + COORDINATE_DESC_1 + ADDRESS_DESC_1
//                + TAG_DESC_FRIEND;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a carpark with all fields same as another carpark in the address book except phone and email
//         * -> added
//         */
//        toAdd = new CarparkBuilder(AMY).withPhone(VALID_CARPARK_TYPE_2).withEmail(VALID_COORDINATE_2).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add to empty address book -> added */
//        deleteAllPersons();
//        assertCommandSuccess(ALICE);
//
//        /* Case: add a carpark with tags, command with parameters in random order -> added */
//        toAdd = BOB;
//        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + CARPARK_TYPE_DESC_2 + ADDRESS_DESC_2 + CARPARK_NO_DESC_2
//                + TAG_DESC_HUSBAND + COORDINATE_DESC_2;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a carpark, missing tags -> added */
//        assertCommandSuccess(HOON);
//
//        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */
//
//        /* Case: filters the carpark list before adding -> added */
//        showPersonsWithName(KEYWORD_MATCHING_MEIER);
//        assertCommandSuccess(IDA);
//
//        /* ------------------------ Perform add operation while a carpark card is selected --------------------------- */
//
//        /* Case: selects first card in the carpark list, add a carpark -> added, card selection remains unchanged */
//        selectPerson(Index.fromOneBased(1));
//        assertCommandSuccess(CARL);
//
//        /* ----------------------------------- Perform invalid add operations --------------------------------------- */
//
//        /* Case: add a duplicate carpark -> rejected */
//        command = PersonUtil.getAddCommand(HOON);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate carpark except with different phone -> rejected */
//        toAdd = new CarparkBuilder(HOON).withPhone(VALID_CARPARK_TYPE_2).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate carpark except with different email -> rejected */
//        toAdd = new CarparkBuilder(HOON).withEmail(VALID_COORDINATE_2).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate carpark except with different address -> rejected */
//        toAdd = new CarparkBuilder(HOON).withAddress(VALID_ADDRESS_2).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate carpark except with different tags -> rejected */
//        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: missing name -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_TYPE_DESC_1 + COORDINATE_DESC_1 + ADDRESS_DESC_1;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing phone -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_1 + COORDINATE_DESC_1 + ADDRESS_DESC_1;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing email -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_1 + CARPARK_TYPE_DESC_1 + ADDRESS_DESC_1;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing address -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_1 + CARPARK_TYPE_DESC_1 + COORDINATE_DESC_1;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: invalid keyword -> rejected */
//        command = "adds " + PersonUtil.getPersonDetails(toAdd);
//        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: invalid name -> rejected */
//        command = AddCommand.COMMAND_WORD + INVALID_CARPARK_NO_DESC + CARPARK_TYPE_DESC_1 + COORDINATE_DESC_1 + ADDRESS_DESC_1;
//        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
//
//        /* Case: invalid phone -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_1 + INVALID_CARPARK_TYPE_DESC + COORDINATE_DESC_1 + ADDRESS_DESC_1;
//        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);
//
//        /* Case: invalid email -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_1 + CARPARK_TYPE_DESC_1 + INVALID_COORDINATE_DESC + ADDRESS_DESC_1;
//        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);
//
//        /* Case: invalid address -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_1 + CARPARK_TYPE_DESC_1 + COORDINATE_DESC_1 + INVALID_ADDRESS_DESC;
//        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);
//
//        /* Case: invalid tag -> rejected */
//        command = AddCommand.COMMAND_WORD + CARPARK_NO_DESC_1 + CARPARK_TYPE_DESC_1 + COORDINATE_DESC_1 + ADDRESS_DESC_1
//                + INVALID_TAG_DESC;
//        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
//    }
//
//    /**
//     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
//     * 1. Command box displays an empty string.<br>
//     * 2. Command box has the default style class.<br>
//     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
//     * {@code toAdd}.<br>
//     * 4. {@code Storage} and {@code CarparkListPanel} equal to the corresponding components in
//     * the current model added with {@code toAdd}.<br>
//     * 5. Browser url and selected card remain unchanged.<br>
//     * 6. Status bar's sync status changes.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(Person toAdd) {
//        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
//     * instead.
//     * @see AddCommandSystemTest#assertCommandSuccess(Person)
//     */
//    private void assertCommandSuccess(String command, Person toAdd) {
//        Model expectedModel = getModel();
//        expectedModel.addPerson(toAdd);
//        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
//
//        assertCommandSuccess(command, expectedModel, expectedResultMessage);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
//     * the,<br>
//     * 1. Result display box displays {@code expectedResultMessage}.<br>
//     * 2. {@code Storage} and {@code CarparkListPanel} equal to the corresponding components in
//     * {@code expectedModel}.<br>
//     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays {@code command}.<br>
//     * 2. Command box has the error style class.<br>
//     * 3. Result display box displays {@code expectedResultMessage}.<br>
//     * 4. {@code Storage} and {@code CarparkListPanel} remain unchanged.<br>
//     * 5. Browser url, selected card and status bar remain unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
