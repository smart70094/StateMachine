package application;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;





public class SampleController {
	@FXML
	public Pane rightPane;
	@FXML 
	public TextField text;
	@FXML 
	public ImageView redo;
	@FXML 
	public ImageView undo;
	@FXML 
	public ImageView edit;
	@FXML 
	public ImageView boundary;
	@FXML 
	public ImageView state;
	@FXML 
	public ImageView initialStates;
	@FXML 
	public ImageView finalStates;
	@FXML 
	public ImageView transition;
	@FXML 
	public ImageView stateActions;
	@FXML 
	public ImageView selfTransitions;
	@FXML 
	public ImageView compoundStates;
	@FXML 
	public ImageView version;
	@FXML 
	public ImageView Trash;
	
	
	@FXML
	protected void redoClick(MouseEvent event){	
		redo.setImage(new Image("/Redo_click.png"));
		 text.setText("redotest");	
	}
	@FXML
	protected void redoExit(MouseEvent event){	
		redo.setImage(new Image("/Redo.png"));
		 text.setText("redo");	
	}
	@FXML
	protected void undoClick(MouseEvent event){	
		undo.setImage(new Image("/Undo_click.png"));
		 text.setText("undotest");	
	}
	@FXML
	protected void undoExit(MouseEvent event){	
		undo.setImage(new Image("/Undo.png"));
		 text.setText("undo");	
	}	
	@FXML
	protected void editClick(MouseEvent event){	
		edit.setImage(new Image("/Edit_click.png"));
		 text.setText("exittest");	
	}
	@FXML
	protected void editExit(MouseEvent event){	
		edit.setImage(new Image("/Edit.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void boundaryClick(MouseEvent event){	
		boundary.setImage(new Image("/Boundary_click.png"));
		 text.setText("boundarytest");	
	}
	@FXML
	protected void boundaryExit(MouseEvent event){	
		boundary.setImage(new Image("/Boundary.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void stateClick(MouseEvent event){	
		state.setImage(new Image("/State_click.png"));
		 text.setText("Statetest");	
	}
	@FXML
	protected void stateExit(MouseEvent event){	
		state.setImage(new Image("/State.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void initialStatesClick(MouseEvent event){	
		initialStates.setImage(new Image("/Initial States_click.png"));
		 text.setText("initalStates");	
	}
	@FXML
	protected void initialStatesExit(MouseEvent event){	
		initialStates.setImage(new Image("/Initial States.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void finalStatesClick(MouseEvent event){	
		finalStates.setImage(new Image("/Final States_click.png"));
		 text.setText("finallStates");	
	}
	@FXML
	protected void finalStatesExit(MouseEvent event){	
		finalStates.setImage(new Image("/Final States.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void transitionClick(MouseEvent event){	
		transition.setImage(new Image("/Transition_click.png"));
		 text.setText("transition");	
	}
	@FXML
	protected void transitionExit(MouseEvent event){	
		transition.setImage(new Image("/Transition.png"));
		 text.setText("exit");	
	}
	@FXML
	protected void stateActionsClick(MouseEvent event){	
		stateActions.setImage(new Image("/State Actions_click.png"));
		 text.setText("State Actions");	
	}
	@FXML
	protected void stateActionsExit(MouseEvent event){	
		stateActions.setImage(new Image("/State Actions.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void selfTransitionsClick(MouseEvent event){	
		selfTransitions.setImage(new Image("/Self-Transitions_click.png"));
		 text.setText("selfTransitions");	
	}
	@FXML
	protected void selfTransitionsExit(MouseEvent event){	
		selfTransitions.setImage(new Image("/Self-Transitions.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void compoundStatesClick(MouseEvent event){	
		compoundStates.setImage(new Image("/Compound States_click.png"));
		 text.setText("compoundStates");	
	}
	@FXML
	protected void compoundStatesExit(MouseEvent event){	
		compoundStates.setImage(new Image("/Compound States.png"));
		 text.setText("exit");	
	}	
	@FXML
	protected void versionClick(MouseEvent event){	
		
		final ChoiceDialog<String> choiceDialog = new ChoiceDialog("version1","version2");
		choiceDialog.setTitle("版本選取");
		choiceDialog.setHeaderText("");
		choiceDialog.setContentText("請選取您的版本：");
		final Optional<String> opt = choiceDialog.showAndWait();
		String rtn;
		try{
		    rtn = opt.get(); //可以直接用「choiceDialog.getResult()」來取代
		}catch(final NoSuchElementException ex){
		    rtn = null;
		}
		/*
		if(rtn == null){
		    //沒有確認輸入文字，而是直接關閉對話框
		    System.out.println("沒有回答");
		}else{
		    System.out.println("您的回答是：" + rtn);
		}
		*/
		version.setImage(new Image("/version.png"));
		 text.setText("version");	
	}
	@FXML
	protected void versionExit(MouseEvent event){	
		
		 version.setImage(new Image("/version_click.png"));
		 text.setText("exit");	
	}
	@FXML
	protected void trashClick(MouseEvent event){	
		
		 Trash.setImage(new Image("/trash_click.png"));
		 text.setText("trash");	
	}
	@FXML
	protected void trashExit(MouseEvent event){	
		
		 Trash.setImage(new Image("/trash.png"));
		 text.setText("Exit");	
	}
	
	
	
	
	
}
